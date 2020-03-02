/**
 * 
 */
package com.wm.test.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wm.test.dao.ApplicationDao;
import com.wm.test.domain.Reservation;
import com.wm.test.domain.Seat;
import com.wm.test.domain.VenueLevel;
import com.wm.test.dto.SeatHold;
import com.wm.test.save.HoldRequest;
import com.wm.test.save.ReserveRequest;
import com.wm.test.service.fixture.ServiceTestFixture;
import com.wm.test.springconfig.ServiceTestConfig;

// TODO: Auto-generated Javadoc
/**
 * The Class TestTicketServiceImpl.
 *
 * @author Sachin Garg
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ServiceTestConfig.class })
public class TestTicketServiceImpl {

	/** The ticket service. */
	private TicketService ticketService;

	/** The mock application dao. */
	@Autowired private ApplicationDao mockApplicationDao;

	/** The stf. */
	private ServiceTestFixture stf;

	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@Before
	public void setUp() throws Exception {
		ticketService = new TicketServiceImpl(mockApplicationDao);
		stf = new ServiceTestFixture();		
	}


	/**
	 * Test method for {@link com.wm.test.service.TicketServiceImpl#numSeatsAvailable(java.util.Optional)}.
	 */
	@Test
	public final void testNumSeatsAvailable() {
		ServiceTestFixture stf = new ServiceTestFixture();
		List<Seat> seatList = stf.createSeatList();
		//System.out.println("SeatList :: " + seatList);
		when(mockApplicationDao.getAvailableSeats((Optional<Integer>)anyObject())).thenReturn(seatList);		
		int numOfSeats = ticketService.numSeatsAvailable(stf.createVenueLevel(1L));
		//System.out.println("NN :: " + numOfSeats);
		assertEquals(numOfSeats, seatList.size());
	}

	/**
	 * Test method for {@link com.wm.test.service.TicketServiceImpl#findAndHoldSeats(int, java.util.Optional, java.util.Optional, java.lang.String)}.
	 */
	@Test
	public final void testFindAndHoldSeats() {

		HoldRequest hrd = stf.createHoldRequest();
		Reservation rr = stf.createReservation();
		when(mockApplicationDao.getAllSeats()).thenReturn(stf.createAVSeatList());
		when(mockApplicationDao.getSeats((List<Long>)anyObject())).thenReturn(stf.createAVSeatList());
		when(mockApplicationDao.reserve(rr)).thenReturn(rr.getId());
		SeatHold sh = ticketService.findAndHoldSeats(hrd.getNumberOfSeats(), Optional.of(hrd.getMinLevel()), Optional.of(hrd.getMaxLevel()), hrd.getEmail());
		assertNotNull(sh);
		assertEquals(Integer.valueOf(sh.getSeats().size()),hrd.getNumberOfSeats());
	}

	/**
	 * Test method for {@link com.wm.test.service.TicketServiceImpl#findAndHoldSeats(int, java.util.Optional, java.util.Optional, java.lang.String)}.
	 */
	@Test
	public final void testFindAndHoldSeatsException() {

		HoldRequest hrd = stf.createHoldRequestException();
		Reservation rr = stf.createReservation();
		when(mockApplicationDao.getAllSeats()).thenReturn(stf.createAVSeatList());
		when(mockApplicationDao.getSeats((List<Long>)anyObject())).thenReturn(stf.createAVSeatList());
		when(mockApplicationDao.reserve(rr)).thenReturn(rr.getId());
		SeatHold sh = ticketService.findAndHoldSeats(hrd.getNumberOfSeats(), Optional.of(hrd.getMinLevel()), Optional.of(hrd.getMaxLevel()), hrd.getEmail());
		assertNotNull(sh);
		assertEquals(sh.getSeats().size(),0);
	}

	/**
	 * Test method for {@link com.wm.test.service.TicketServiceImpl#findAndHoldSeats(int, java.util.Optional, java.util.Optional, java.lang.String)}.
	 */
	@Test
	public final void testFindAndHoldSeatsNotSufficietSeats() {

		HoldRequest hrd = stf.createHoldRequestException();
		Reservation rr = stf.createReservation();
		when(mockApplicationDao.getAllSeats()).thenReturn(stf.createAVAndHoldSeatList());
		when(mockApplicationDao.getSeats((List<Long>)anyObject())).thenReturn(stf.createAVAndHoldSeatList());
		when(mockApplicationDao.reserve(rr)).thenReturn(rr.getId());
		SeatHold sh = ticketService.findAndHoldSeats(hrd.getNumberOfSeats(), Optional.of(hrd.getMinLevel()), Optional.of(hrd.getMaxLevel()), hrd.getEmail());
		assertNotNull(sh);
		assertEquals(sh.getSeats().size(),0);
	}

	/**
	 * Test method for {@link com.wm.test.service.TicketServiceImpl#findAndHoldSeats(int, java.util.Optional, java.util.Optional, java.lang.String)}.
	 */
	@Test
	public final void testFindAndHoldSeatsNoSeats() {

		HoldRequest hrd = stf.createHoldRequest();
		Reservation rr = stf.createReservation();
		when(mockApplicationDao.getAllSeats()).thenReturn(stf.createSeatList());
		when(mockApplicationDao.getSeats((List<Long>)anyObject())).thenReturn(stf.createSeatList());
		when(mockApplicationDao.reserve(rr)).thenReturn(rr.getId());
		SeatHold sh = ticketService.findAndHoldSeats(hrd.getNumberOfSeats(), Optional.of(hrd.getMinLevel()), Optional.of(hrd.getMaxLevel()), hrd.getEmail());
		assertEquals(sh.getId(), Long.valueOf(0L));
		assertEquals(sh.getSeats().size(),0);
	}

	/**
	 * Test method for {@link com.wm.test.service.TicketServiceImpl#reserveSeats(int, java.lang.String)}.
	 */
	@Test
	public final void testReserveSeats() {
		ReserveRequest  rr = stf.createReserveRequest();
		Reservation res = stf.createReservation();
		when(mockApplicationDao.getRegistration(Long.valueOf(rr.getId()), rr.getEmail())).thenReturn(res);
		assertEquals(String.valueOf(res.getId()),ticketService.reserveSeats(rr.getId(), rr.getEmail()));
	}

	/**
	 * Test method for {@link com.wm.test.service.TicketServiceImpl#reserveSeats(int, java.lang.String)}.
	 */
	@Test
	public final void testReserveSeatsException() {
		ReserveRequest  rr = stf.createReserveRequest();
		when(mockApplicationDao.getRegistration(Long.valueOf(rr.getId()), rr.getEmail())).thenReturn(null);
		assertEquals("-1",ticketService.reserveSeats(rr.getId(), rr.getEmail()));
	}

	/**
	 * Test method for {@link com.wm.test.service.TicketServiceImpl#holdLogin()}.
	 */
	@Test
	public final void testHoldLogin() {
		List<Reservation> resList = stf.createReservationList();

		when(mockApplicationDao.getHeldRegistration(anyLong())).thenReturn(resList);
		doNothing().when(mockApplicationDao).removeReservations((List<Reservation>)anyObject());

		ticketService.holdLogin();
		verify(mockApplicationDao, times(1)).getHeldRegistration(anyLong());
		verify(mockApplicationDao, times(1)).removeReservations((List<Reservation>)anyObject());
	}

	/**
	 * Test method for {@link com.wm.test.service.TicketServiceImpl#createData()}.
	 */
	@Test
	public final void testCreateData() {

		TicketServiceImpl ticketServiceImpl = new TicketServiceImpl(mockApplicationDao);

		doNothing().when(mockApplicationDao).saveOrUpdate((VenueLevel)anyObject());
		doNothing().when(mockApplicationDao).saveOrUpdate((Seat)anyObject());

		ticketServiceImpl.createData();

		verify(mockApplicationDao, times(4)).saveOrUpdate((VenueLevel)anyObject());	

		verify(mockApplicationDao, times(4)).saveOrUpdate((VenueLevel)anyObject());		

	}

}
