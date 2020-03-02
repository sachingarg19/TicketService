package com.wm.test.e2e;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wm.test.bootstrap.RootConfig;
import com.wm.test.controller.ApplicationController;
import com.wm.test.dto.ReservationDetail;
import com.wm.test.dto.SeatHold;
import com.wm.test.dto.SeatsPerLevel;
import com.wm.test.e2e.fixture.EndToEndTestFixture;
import com.wm.test.exception.TicketNotFoundException;
import com.wm.test.save.HoldRequest;
import com.wm.test.save.ReserveRequest;
import com.wm.test.service.TicketService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RootConfig.class })
public class TestEndToEnd {


	//private MockMvc mockMvc;
	@Autowired private TicketService mockTicketService;

	private EndToEndTestFixture ete;

	private ApplicationController ac ;

	@Before
	public void setUp() {
		//mockMvc = MockMvcBuilders.standaloneSetup(new ApplicationController(mockTicketService)).build();
		ac = new ApplicationController(mockTicketService);
		ete = new EndToEndTestFixture();
	}

	@Test
	public void testGetAvailableSeats() throws Exception {

		SeatsPerLevel seatsPerLevel = ete.createSeatsPerLevel();
		SeatsPerLevel  sL = ac.getAvailableSeats(1L);
		assertNotNull(sL);
		assertEquals(sL.getLevelName(), seatsPerLevel.getLevelName());
		assertEquals(sL.getNumOfSeats(),seatsPerLevel.getNumOfSeats());

	}
	/**
	 * Test method for {@link com.wm.test.controller.ApplicationController#reserve(com.wm.test.save.ReserveRequest)}.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	@Test
	public final void testReserve() throws IOException, Exception{
		ReserveRequest reserveRequest = ete.createReserveRequest();
		//ReservationDetail reservationDetail = ete.createReservationDetail();
		ReservationDetail rd = ac.reserve(reserveRequest);
		assertNotNull(rd);
	}

	/**
	 * Test method for {@link com.wm.test.controller.ApplicationController#holdSeats(com.wm.test.save.HoldRequest)}.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	@Test
	public final void testHoldSeats() throws IOException, Exception {
		SeatHold seatHold = ete.createSeatHold();
		HoldRequest holdRequest = ete.createHoldRequest();

		SeatHold sh = ac.holdSeats(holdRequest);
		assertNotNull(sh);
		assertEquals(sh.getId(), seatHold.getId());
		assertEquals(sh.getSeats().size(), seatHold.getSeats().size());
	}

	/**
	 * Test method for {@link com.wm.test.controller.ApplicationController#handleTicketNotFoundException(com.wm.test.exception.TicketNotFoundException)}.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public final void testHandleTicketNotFoundException() throws Exception {
		final String errorMessage = "Mocking 404 message";
		String ex = ac.handleTicketNotFoundException(new TicketNotFoundException(errorMessage));
		assertNotNull(ex);
		assertEquals(ex, errorMessage);
	}

}
