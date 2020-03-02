/**
 * 
 */
package com.wm.test.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wm.test.controller.fixture.ControllerTestFixture;
import com.wm.test.dto.ReservationDetail;
import com.wm.test.dto.SeatHold;
import com.wm.test.dto.SeatsPerLevel;
import com.wm.test.exception.TicketNotFoundException;
import com.wm.test.save.HoldRequest;
import com.wm.test.save.ReserveRequest;
import com.wm.test.service.TicketService;
import com.wm.test.springconfig.ControllerTestConfig;

// TODO: Auto-generated Javadoc
/**
 * The Class TestApplicationController.
 *
 * @author Sachin Garg
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ControllerTestConfig.class })
//@WebAppConfiguration
public class TestApplicationController {

	/** The mock ticket service. */
	@Autowired private TicketService mockTicketService;

	/** The mock mvc. */
	//private MockMvc mockMvc;

	private ApplicationController ac;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@Before
	public void setUp() throws Exception {
		//mockMvc = MockMvcBuilders.standaloneSetup(new ApplicationController(mockTicketService)).build();
		ac = new ApplicationController(mockTicketService);
	}

	/**
	 * Test method for {@link com.wm.test.controller.ApplicationController#reserve(com.wm.test.save.ReserveRequest)}.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	@Test
	public final void testReserve() throws IOException, Exception{
		ControllerTestFixture f = new ControllerTestFixture();
		ReserveRequest reserveRequest = f.createReserveRequest();
		ReservationDetail reservationDetail = f.createReservationDetail();
		when(mockTicketService.reserveSeats(anyInt(),anyString())).thenReturn("1");

		ReservationDetail rd = ac.reserve(reserveRequest);
		assertNotNull(rd);
		assertEquals(rd.getReservationID(), reservationDetail.getReservationID());
		assertEquals(rd.getEmailID(), reservationDetail.getEmailID());

		/*mockMvc.perform(post("/reserve").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reserveRequest))
				.accept(TestUtil.APPLICATION_JSON_UTF8)
				)
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.reservationID", is(reservationDetail.getReservationID())))
		.andExpect(jsonPath("$.emailID", is(reservationDetail.getEmailID())))	
		.andReturn();*/		
	}

	/**
	 * Test method for {@link com.wm.test.controller.ApplicationController#reserve(com.wm.test.save.ReserveRequest)}.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	@Test
	public final void testReserveException() throws IOException, Exception{
		ControllerTestFixture f = new ControllerTestFixture();
		ReserveRequest reserveRequest = f.createReserveRequest();
		ReservationDetail reservationDetail = f.createReservationDetail();
		when(mockTicketService.reserveSeats(anyInt(),anyString())).thenReturn("-1");

		ReservationDetail rd = ac.reserve(reserveRequest);
		assertNotNull(rd);
		assertNull(rd.getReservationID());
		assertNull(rd.getEmailID());
		assertEquals(rd.getMessage(), reservationDetail.getMessage());

		/*mockMvc.perform(post("/reserve").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reserveRequest))
				.accept(TestUtil.APPLICATION_JSON_UTF8)
				)
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.message", is(reservationDetail.getMessage())))			
		.andReturn();	*/	
	}

	/**
	 * Test method for {@link com.wm.test.controller.ApplicationController#holdSeats(com.wm.test.save.HoldRequest)}.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	@Test
	public final void testHoldSeats() throws IOException, Exception {
		ControllerTestFixture f = new ControllerTestFixture();
		SeatHold seatHold = f.createSeatHold();
		HoldRequest holdRequest = f.createHoldRequest();

		when(mockTicketService.findAndHoldSeats(anyInt(),(Optional<Integer>)anyObject(),(Optional<Integer>)anyObject(),anyString())).thenReturn(seatHold);

		SeatHold sh = ac.holdSeats(holdRequest);
		assertNotNull(sh);
		assertEquals(sh.getId(), seatHold.getId());
		assertEquals(sh.getSeats().size(), seatHold.getSeats().size());

		/*mockMvc.perform(post("/hold").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(holdRequest))
				.accept(TestUtil.APPLICATION_JSON_UTF8)
				)
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id", is(1)))
		.andExpect(jsonPath("$.seats", Matchers.hasSize(3)))	
		.andReturn();	*/	
	}

	/**
	 * Test method for {@link com.wm.test.controller.ApplicationController#getAvailableSeats(java.lang.Long)}.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public final void testGetAvailableSeats() throws Exception {
		ControllerTestFixture f = new ControllerTestFixture();
		SeatsPerLevel seatsPerLevel = f.createSeatsPerLevel();
		//Optional<Integer> venueLevel = f.createVenueLevel(Long.valueOf(1));
		when(mockTicketService.numSeatsAvailable((Optional<Integer>)anyObject())).thenReturn(1250);

		SeatsPerLevel  sL = ac.getAvailableSeats(1L);
		assertNotNull(sL);
		assertEquals(sL.getLevelName(), seatsPerLevel.getLevelName());
		assertEquals(sL.getNumOfSeats(),seatsPerLevel.getNumOfSeats());

		/*mockMvc.perform(get("/getAvailableSeats/{id}", 1))
		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.levelName", is(seatsPerLevel.getLevelName())))
		.andExpect(jsonPath("$.numOfSeats", is(1250)))
		.andReturn();*/
	}

	/**
	 * Test method for {@link com.wm.test.controller.ApplicationController#handleTicketNotFoundException(com.wm.test.exception.TicketNotFoundException)}.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public final void testHandleTicketNotFoundException() throws Exception {
		final String errorMessage = "Mocking 404 message";
		when(mockTicketService.numSeatsAvailable((Optional<Integer>)anyObject())).thenAnswer(new Answer<SeatsPerLevel>() {
			public SeatsPerLevel answer(InvocationOnMock invocation) throws Throwable {
				throw new TicketNotFoundException(errorMessage);
			}
		});

		String ex = ac.handleTicketNotFoundException(new TicketNotFoundException(errorMessage));
		assertNotNull(ex);
		assertEquals(ex, errorMessage);

		/*mockMvc.perform(get("/getAvailableSeats/{id}", -1).accept(TestUtil.APPLICATION_JSON_UTF8))
		.andExpect(status().isNotFound())
		.andExpect(content().string(errorMessage))
		.andReturn();*/
	}

}
