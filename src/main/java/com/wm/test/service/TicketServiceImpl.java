package com.wm.test.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.wm.test.controller.ApplicationController;
import com.wm.test.dao.ApplicationDao;
import com.wm.test.domain.Reservation;
import com.wm.test.domain.ReservationStatus;
import com.wm.test.domain.Seat;
import com.wm.test.domain.VenueLevel;
import com.wm.test.dto.SeatHold;

// TODO: Auto-generated Javadoc
/**
 * The Class TicketServiceImpl.
 *
 * @author Sachin Garg
 */
@Service
public class TicketServiceImpl implements TicketService {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(TicketServiceImpl.class);


	/** The application dao. */
	private ApplicationDao applicationDao;

	/**
	 * Instantiates a new ticket service impl.
	 *
	 * @param applicationDao
	 *            the application dao
	 */
	@Autowired
	public TicketServiceImpl(ApplicationDao applicationDao) {
		super();
		this.applicationDao = applicationDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.wm.test.service.TicketService#numSeatsAvailable(java.util.Optional)
	 */
	@Override
	@Transactional
	public int numSeatsAvailable(Optional<Integer> venueLevel) {
		logger.info("Enter numSeatsAvailable - venueLevel -" + venueLevel);
		return applicationDao.getAvailableSeats(venueLevel).size();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wm.test.service.TicketService#findAndHoldSeats(int,
	 * java.util.Optional, java.util.Optional, java.lang.String)
	 */
	@Override
	@Transactional
	public SeatHold findAndHoldSeats(int numSeats, Optional<Integer> minLevel, Optional<Integer> maxLevel,
			String customerEmail) {
		logger.info("Enter findAndHoldSeats -Num Of Seats - " + numSeats + " - email - " + customerEmail
				+ "- Min Level -" + minLevel + "- Max Level -" + maxLevel);

		// int maxLevelD = -1 ;
		// int minLevelD = -1 ;
		//List<Seat> seats = applicationDao.getAllSeats();
		//logger.debug("Total Num Of Seats - " + seats.size());
		
		List<Long> maxSeatLong = new ArrayList<Long>();
		maxSeatLong.add(Long.valueOf(maxLevel.get().longValue()));
		List<Seat> seatsMaxLevel = applicationDao.getSeats(maxSeatLong);
		logger.debug("Max Level Num Of Seats - " + seatsMaxLevel.size());

		Reservation reservation = new Reservation();
		reservation.setEmail(customerEmail);
		reservation.setStatus(ReservationStatus.HELD);
		//First Gives Preference to Max level to find out the seat
		String levelInfo = maxLevel.get().toString();
		List<Seat> bestSeats = new ArrayList<Seat>();
		List<Long> held = new ArrayList<Long>();
		try{
			bestSeats = seatLookUP(seatsMaxLevel,numSeats);//,minLevel,maxLevel);
			logger.info("Size of Best Seats from Max Level:: " + bestSeats.size());
			if(bestSeats.isEmpty() || bestSeats.size() < numSeats){
				List<Long> minSeatLong = new ArrayList<Long>();
				minSeatLong.add(Long.valueOf(minLevel.get().longValue()));		
				List<Seat> seatsMinLevel = applicationDao.getSeats(minSeatLong);
				logger.debug(" Min level Num Of Seats - " + seatsMinLevel.size());
				bestSeats = new ArrayList<Seat>();
				levelInfo = minLevel.get().toString();
				bestSeats = seatLookUP(seatsMinLevel,numSeats);//,minLevel,maxLevel);
				logger.info("Size of Best Seats from Min Level:: " + bestSeats.size());
			}
		}catch(Exception e){
			return new SeatHold(Long.valueOf(0L), "", held,"Either Not Sufficient Seats Are Available or All Are Booked. Please try different Level.");
		}
		//logger.debug("Num of BestSeats -- " + bestSeats);
		if(bestSeats.isEmpty() || bestSeats.size() < numSeats){
			return new SeatHold(Long.valueOf(0L), "", held,"Either Not Sufficient Seats Are Available or All Are Booked. Please try different Level.");
		}
		// applicationDao.reserve(reservation);
		List<Seat> seatsOnHold = new ArrayList<Seat>();
		for (int i = 0; i < numSeats; i++) {
			// seatsOnHold.add(seats.get(0));
			seatsOnHold.add(bestSeats.get(i));
		}
		reservation.setSeats(seatsOnHold);

		long reserve = applicationDao.reserve(reservation);
		logger.debug("Reserve Hold ID : " + reserve);

		//SeatHold hold = new SeatHold(reserve, held);
		SeatHold hold = new SeatHold();
		hold.setId(reserve);		
		for (Seat seat : seatsOnHold) {
			//held.add(seat.getId());
			held.add(Long.valueOf(seat.getNumber()));
		}
		hold.setLevel(levelInfo);
		hold.setSeats(held);		
		hold.setMessage("Seats on hold with your email ID - "+reservation.getEmail()+". Please note hold ID and your email to reserve tickets.");

		for (Seat seat : seatsOnHold) {
			logger.debug("seat.getReservation()=" + seat.getReservation());
		}
		logger.info("Exit findAndHoldSeats - " + hold);
		return hold;
	}

	/**
	 * Seat look up.
	 *
	 * @param seats the seats
	 * @param numSeats the num seats
	 * @param minLevel the min level
	 * @param maxLevel the max level
	 * @return the list
	 * @throws Exception the exception
	 */
	private List<Seat> seatLookUP(List<Seat> seats,int numSeats){
		logger.info("Enter seatLookUP : " + numSeats);//,Optional<Integer> minLevel, Optional<Integer> maxLevel) throws Exception{
		List<Seat> bestSeatList = new ArrayList<Seat>();
		List<Integer> numList = new ArrayList<Integer>();
		int i =1;
		int j = 0;

		while(j < seats.size()){//0,1,2
			while(i <= numSeats){//1,2,3
				if(null == seats.get(j).getReservation()){//true
					if(numSeats == 1){
						numList.add(seats.get(j).getNumber());
						bestSeatList.add(seats.get(j));
						logger.info("Num List 1: " + numList);
						return bestSeatList;
					}
					if(null == seats.get(i).getReservation()){//true
						if(seats.get(j).getNumber()+1 == seats.get(i).getNumber()) {//true 1+1 = 2, 2+1 = 3, 3+1 = 4
							if(numList.contains(seats.get(j).getNumber())){
								numList.add(seats.get(i).getNumber()); //size - 1, 3, -- 2,
								bestSeatList.add(seats.get(i));//1,3
							}else{
								numList.add(seats.get(j).getNumber()); //size - 1, 3, -- 2,
								bestSeatList.add(seats.get(j));//1,3
							}
							if(numSeats > bestSeatList.size()){//2 > 1, 3 > 3
								if(numList.contains(seats.get(i).getNumber())){
									numList.add(seats.get(i+1).getNumber());	
									bestSeatList.add(seats.get(i+1)); //2
									i++;
								}else{
									numList.add(seats.get(i).getNumber());	
									bestSeatList.add(seats.get(i)); //2
								}
							}
							i++; //1+1 , 1+1+1							
							if(i >= numSeats){//2 >= 2, 3>=3
								logger.debug("i == num of Seats :: Num List :: " + numList);
								return bestSeatList;
							}
							logger.debug("Num List :: " + numList);
						}
					}	
					logger.debug("Num List :: " + numList);
					break;
				}	
				logger.debug("Num List :: " + numList);
				break;
			}	
			j++;

		}
		return bestSeatList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wm.test.service.TicketService#reserveSeats(int,
	 * java.lang.String)
	 */
	@Override
	@Transactional
	public String reserveSeats(int seatHoldId, String customerEmail) {
		logger.info("Enter reserveSeats - seatHoldId - " + seatHoldId + "-customerEmail-" + customerEmail);
		Reservation reservation = applicationDao.getRegistration(new Integer(seatHoldId).longValue(), customerEmail);
		if (reservation != null) {
			reservation.setStatus(ReservationStatus.RESERVED);
			applicationDao.reserve(reservation);
			logger.debug("Reservation ID : " + reservation.getId());
			return "" + reservation.getId();
		} else{
			logger.debug("Either Already reserved or Reservation ID doesn't exists -" + -1);
			return "-1";// "{\"Either Already reserved or Reservation ID doesn't
			// exists\":-1}";
		}
	}

	/** The tx manager. */
	@Autowired
	@Qualifier("transactionManager")
	protected PlatformTransactionManager txManager;

	/**
	 * Inits the.
	 */
	@PostConstruct
	public void init() {
		logger.info("Enter Post Construct --Init");
		TransactionTemplate tmpl = new TransactionTemplate(txManager);
		tmpl.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				createData();
			}
		});
		logger.info("Exit Post Construct --Init");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.wm.test.service.TicketService#holdLogin()
	 */
	@Scheduled(cron = "*/10 * * * * ?")
	@Transactional
	public void holdLogin() {
		logger.info("------------Timer Task of 10 in progress ---------");
		List<Reservation> reservations = applicationDao.getHeldRegistration(System.currentTimeMillis() / 1000 - 20);
		logger.debug("reservations.size " + reservations.size());
		applicationDao.removeReservations(reservations);

	}

	/**
	 * Creates the data.
	 */
	@Transactional
	public void createData() {
		logger.info("------------Enter createData to perform initial DataSetup ---------------");
		VenueLevel orchLevel = new VenueLevel("Orchestra", 1, new Double(100.00), 25, 50);
		VenueLevel mainLevel = new VenueLevel("Main", 2, new Double(75.00), 20, 100);
		VenueLevel balCony1Level = new VenueLevel("Balcony 1", 3, new Double(50.00), 15, 100);
		VenueLevel balCony2Level = new VenueLevel("Balcony 2", 4, new Double(40.00), 15, 100);
		applicationDao.saveOrUpdate(orchLevel);
		logger.debug("orchLevel ID - " + orchLevel.getId());
		applicationDao.saveOrUpdate(mainLevel);
		logger.debug("mainLevel ID - " + mainLevel.getId());

		applicationDao.saveOrUpdate(balCony1Level);
		logger.debug("balCony1Level ID - " + balCony1Level.getId());
		applicationDao.saveOrUpdate(balCony2Level);
		logger.debug("balCony1Level ID - " + balCony2Level.getId());

		for (int i = 1; i <= 25; i++) {
			for (int j = 1; j <= 50; j++) {
				if (null != orchLevel.getId()) {
					Seat seat = new Seat(orchLevel.getId(), i);
					seat.setNumber(j);
					applicationDao.saveOrUpdate(seat);
					if (seat.getReservation() != null) {
						logger.debug("orchLevel Seat Reservation ID -- " + seat.getReservation().getId());
					}
				}
			}
		}
		for (int i = 1; i <= 20; i++) {
			for (int j = 1; j <= 100; j++) {
				if (null != mainLevel.getId()) {
					Seat seat = new Seat(mainLevel.getId(), i);
					seat.setNumber(j);
					applicationDao.saveOrUpdate(seat);
					if (seat.getReservation() != null) {
						logger.debug("mainLevel Seat Reservation ID -- " + seat.getReservation().getId());
					}
				}
			}
		}

		for (int i = 1; i <= 15; i++) {
			for (int j = 1; j <= 100; j++) {
				if (null != balCony1Level.getId()) {
					Seat seat = new Seat(balCony1Level.getId(), i);
					seat.setNumber(j);
					applicationDao.saveOrUpdate(seat);
					if (seat.getReservation() != null) {
						logger.debug("balCony1Level Seat Reservation ID -- " + seat.getReservation().getId());
					}
				}
			}
		}

		for (int i = 1; i <= 15; i++) {
			for (int j = 1; j <= 100; j++) {
				if (null != balCony2Level.getId()) {
					Seat seat = new Seat(balCony2Level.getId(), i);
					seat.setNumber(j);
					applicationDao.saveOrUpdate(seat);
					if (seat.getReservation() != null) {
						logger.debug("balCony2Level Seat Reservation ID -- " + seat.getReservation().getId());
					}
				}
			}
		}

		List<Seat> allSeats = applicationDao.getAllSeats();

		logger.info("allSeats=" + allSeats.size());

		logger.info("----------Data Setup SuccessFull --------------");
	}
}
