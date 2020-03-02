package com.wm.test.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.wm.test.domain.Reservation;
import com.wm.test.domain.ReservationStatus;
import com.wm.test.domain.Seat;
import com.wm.test.domain.VenueLevel;


// TODO: Auto-generated Javadoc
/**
 * The Class ApplicationDaoImpl.
 * @author Sachin Garg
 */
@Repository
@Transactional
public class ApplicationDaoImpl implements ApplicationDao {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(ApplicationDaoImpl.class);

	/** The em. */
	@PersistenceContext
	private EntityManager em;


	/**
	 * @return the em
	 */
	public EntityManager getEm() {
		return em;
	}


	/**
	 * @param em the em to set
	 */
	public void setEm(EntityManager em) {
		this.em = em;
	}


	/* (non-Javadoc)
	 * @see com.wm.test.dao.ApplicationDao#reserve(com.wm.test.domain.Reservation)
	 */
	public long reserve(Reservation reservation) {
		logger.info("Enter reserve -- Reservation");
		for(Seat seat: reservation.getSeats()){
			seat.setReservation(reservation);
		}

		reservation.setHeldTime(System.currentTimeMillis()/1000);
		em.persist(reservation);

		em.flush();
		logger.info("Exist reserve -- ReservationID -- " + reservation.getId());
		return reservation.getId();
	}


	/* (non-Javadoc)
	 * @see com.wm.test.dao.ApplicationDao#removeReservations(java.util.List)
	 */
	public void removeReservations(List<Reservation> reservations) {
		logger.info("Enter removeReservations -- Reservation List-- " + reservations.size());
		for(Reservation reservation: reservations){
			//em.remove(reservation);
			//em.flush();
			for(Seat seat: reservation.getSeats()){
				logger.debug("--Removing reservation ID from Seat---");
				Query query = em.createQuery("UPDATE Seat SET reservationId = null WHERE id =:id");
				int updateCount = query.setParameter("id", seat.getId()).executeUpdate();
				logger.info("Updating seat reservation ID to null : " + seat.getId() + " Update Count :: " + updateCount);

				TypedQuery<Seat> createQuery = em.createQuery("SELECT s FROM Seat s where id in :IDS", Seat.class);
				createQuery.setParameter("IDS", seat.getId());
				List<Seat> seatList = createQuery.getResultList();
				for(Seat s : seatList){
					logger.info("Reservation ID now is first : " + s.getReservation());
				}	

				em.flush();

				/*List<Seat> seatList1 = createQuery.getResultList();
				for(Seat s : seatList1){
					logger.info("Reservation ID now is second : " + s.getReservation());
				}*/	
			}

			Query query1 = em.createQuery("DELETE FROM Reservation r WHERE r.id =:id");
			int deletedCount = query1.setParameter("id", reservation.getId()).executeUpdate();
			logger.info("Updating Reservation reservation ID to null : " + reservation.getId() + " Update Count :: " + deletedCount);
		}

		em.flush();
		logger.info("Exist removeReservations");
	}

	/* (non-Javadoc)
	 * @see com.wm.test.dao.ApplicationDao#getSeats(java.util.List)
	 */
	public List<Seat> getSeats(List<Long> seatIds) {
		logger.info("Enter getSeats : " + seatIds);
		/*TypedQuery<Seat> createQuery = em.createQuery("SELECT s FROM Seat s where id in :IDS", Seat.class);
		createQuery.setParameter("IDS", seatIds);*/
		List<Seat> seats = new ArrayList<Seat>();
		TypedQuery<Seat> createQuery = null;
		for(Long level : seatIds){
			createQuery = em.createQuery("SELECT s FROM Seat s where reservationId is null and levelId = :levelId", Seat.class);
			createQuery.setParameter("levelId", level.longValue());
			seats = createQuery.getResultList();						
		}
		logger.info("Exist getSeats : " + seats.size());
		return seats;
	}


	/* (non-Javadoc)
	 * @see com.wm.test.dao.ApplicationDao#getAvailableSeats(java.util.Optional)
	 */
	public List<Seat> getAvailableSeats(Optional<Integer> venueLevel) {		
		logger.info("Enter getAvailableSeats : " + venueLevel);
		TypedQuery<Seat> createQuery = null;
		if(venueLevel.isPresent()){
			createQuery = em.createQuery("SELECT s FROM Seat s where reservationId is null and levelId = :levelId", Seat.class);
			createQuery.setParameter("levelId", venueLevel.get().longValue());
		}else{
			createQuery = em.createQuery("SELECT s FROM Seat s where reservationId is null", Seat.class);
		}
		List<Seat> seats = createQuery.getResultList();
		logger.info("Exist getAvailableSeats : " + seats.size());
		return seats;

	}

	/* (non-Javadoc)
	 * @see com.wm.test.dao.ApplicationDao#getAllSeats()
	 */
	public List<Seat> getAllSeats() {
		logger.info("Enter getAllSeats");
		TypedQuery<Seat> createQuery = em.createQuery("SELECT s FROM Seat s", Seat.class);
		List<Seat> seats = createQuery.getResultList();
		logger.info("Exist getAvailableSeats : " + seats.size());
		return seats;

	}


	/* (non-Javadoc)
	 * @see com.wm.test.dao.ApplicationDao#getHeldRegistration(java.lang.Long)
	 */
	public List<Reservation> getHeldRegistration(Long olddate) {
		logger.info("Enter getHeldRegistration - " + olddate);
		TypedQuery<Reservation> createQuery1 = em.createQuery("SELECT r FROM Reservation r where status = :status and heldTime < :olddate", Reservation.class);
		createQuery1.setParameter("status", ReservationStatus.HELD);
		createQuery1.setParameter("olddate", olddate);

		List<Reservation> reservations = createQuery1.getResultList();
		for(Reservation reservation: reservations)
		{
			logger.info("Email : " + reservation.getEmail());
			logger.info("Id : " + reservation.getId());
			logger.info("Status : " + reservation.getStatus());
		}
		logger.info("Exit getHeldRegistration - " + reservations.size());
		return reservations;
	}


	/* (non-Javadoc)
	 * @see com.wm.test.dao.ApplicationDao#getRegistration(java.lang.Long, java.lang.String)
	 */
	public Reservation getRegistration(Long id, String email) {
		logger.info("Enter getRegistration - ID - " + id + " - email - " + email);
		TypedQuery<Reservation> createQuery = em.createQuery("SELECT r FROM Reservation r where status = :status and id = :ID and email = :email ", Reservation.class);
		createQuery.setParameter("status", ReservationStatus.HELD);
		createQuery.setParameter("ID", id);
		createQuery.setParameter("email", email);
		List<Reservation> re = createQuery.getResultList();
		logger.info("Exit getRegistration - " + re.size());
		if(re.size()==0){
			return null;
		}
		else {
			return re.get(0);
		}

	}

	/* (non-Javadoc)
	 * @see com.wm.test.dao.ApplicationDao#saveOrUpdate(com.wm.test.domain.Seat)
	 */
	public void saveOrUpdate( Seat entity) {
		//		/logger.info("Enter saveOrUpdate - Seat ");
		em.persist(entity);
		em.flush();
	}

	/* (non-Javadoc)
	 * @see com.wm.test.dao.ApplicationDao#saveOrUpdate(com.wm.test.domain.VenueLevel)
	 */
	public void saveOrUpdate( VenueLevel entity) {
		logger.info("Enter saveOrUpdate - VenueLevel");
		em.persist(entity);
		em.flush();

	}





}
