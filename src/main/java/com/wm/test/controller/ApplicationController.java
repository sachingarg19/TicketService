package com.wm.test.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.wm.test.dto.ReservationDetail;
import com.wm.test.dto.SeatHold;
import com.wm.test.dto.SeatsPerLevel;
import com.wm.test.exception.TicketNotFoundException;
import com.wm.test.save.HoldRequest;
import com.wm.test.save.ReserveRequest;
import com.wm.test.service.TicketService;

// TODO: Auto-generated Javadoc
/**
 * REST layer for managing people.
 * 
 * @author Sachin Garg
 */
@Controller
public class ApplicationController {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(ApplicationController.class);


	/** The ticket service. */
	private TicketService ticketService;

	/**
	 * Instantiates a new application controller.
	 *
	 * @param ticketService
	 *            the ticket service
	 */
	@Autowired
	public ApplicationController(TicketService ticketService) {
		this.ticketService = ticketService;
	}

	/**
	 * Reserve.
	 *
	 * @param reserveRequest
	 *            the reserve request
	 * @return the reservation detail
	 */
	@RequestMapping(value = "reserve", method = RequestMethod.POST)
	@ResponseBody
	public ReservationDetail reserve(@RequestBody ReserveRequest reserveRequest) {
		logger.info("Enter reserve -ID - " + reserveRequest.getId() + " - email - " + reserveRequest.getEmail());
		String reservationID = ticketService.reserveSeats(reserveRequest.getId(), reserveRequest.getEmail());
		ReservationDetail reservationDetail = new ReservationDetail();
		if (reservationID != "-1") {
			reservationDetail.setReservationID(reservationID);
			reservationDetail.setEmailID(reserveRequest.getEmail());
			reservationDetail.setMessage("SuccessFully Reserved");
		} else {
			reservationDetail.setMessage("Either Already reserved or Reservation ID doesn't exists:-1");
		}
		logger.info("Exit reserve -RES ID - " + reservationDetail.getReservationID() + " - email - " + reservationDetail.getEmailID());
		return reservationDetail;
	}

	/**
	 * Hold seats.
	 *
	 * @param request
	 *            the request
	 * @return the seat hold
	 */
	@RequestMapping(value = "hold", method = RequestMethod.POST)
	@ResponseBody
	public SeatHold holdSeats(@RequestBody HoldRequest request) {
		logger.info("Enter holdSeats -Num Of Seats - " + request.getNumberOfSeats() + " - email - " + request.getEmail()
		+ "- Min Level -" + request.getMinLevel() + "- Max Level -" + request.getMaxLevel());
		Optional<Integer> minLevel = Optional.empty();
		if (request.getMinLevel() != 0) {
			minLevel = Optional.of(request.getMinLevel());
		}
		Optional<Integer> maxLevel = Optional.empty();
		if (request.getMaxLevel() != 0) {
			maxLevel = Optional.of(request.getMaxLevel());
		}
		// ticketService.findAndHoldSeats(request.getNumberOfSeats(), minLevel,
		// maxLevel, request.getEmail()).getId();
		SeatHold seatHold = ticketService.findAndHoldSeats(request.getNumberOfSeats(), minLevel, maxLevel, request.getEmail());
		logger.info("Exit holdSeats -ID - " + seatHold.getId() + " - Seats - " + seatHold.getSeats() + " Level - " + seatHold.getLevel() + " Message -" + seatHold.getMessage());		
		return seatHold;

	}

	/**
	 * Gets the available seats.
	 *
	 * @param id
	 *            the id
	 * @return the available seats
	 */
	@RequestMapping("getAvailableSeats/{id}")
	@ResponseBody
	public SeatsPerLevel getAvailableSeats(@PathVariable Long id) {
		logger.info("Enter getAvailableSeats -  venueLevel -" + id);		
		Optional<Integer> venueLevel = Optional.empty();
		if (id != 0L) {
			venueLevel = Optional.of(id.intValue());
		}

		SeatsPerLevel seatsPerLevel = new SeatsPerLevel();
		seatsPerLevel.setLevelName("Venue Level " + venueLevel.get());
		seatsPerLevel.setNumOfSeats(ticketService.numSeatsAvailable(venueLevel));		
		logger.info("Exit getAvailableSeats -Seats Per Level - " + seatsPerLevel);
		return seatsPerLevel;

	}

	// --- Error handlers

	/**
	 * Handle ticket not found exception.
	 *
	 * @param e
	 *            the e
	 * @return the string
	 */
	@ExceptionHandler(TicketNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public String handleTicketNotFoundException(TicketNotFoundException e) {
		logger.error("Enter handleTicketNotFoundException : " + e.getMessage());
		return e.getMessage();
	}

}
