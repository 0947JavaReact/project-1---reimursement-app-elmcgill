package com.revature.servicetests;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.revature.dao.ReimbursementDao;
import com.revature.dao.UserDao;
import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementStatus;
import com.revature.models.ReimbursementType;
import com.revature.services.ReimbursementService;
import com.revature.services.UserService;

public class ReimbursementServiceTests {

	@InjectMocks
	public ReimbursementService rServ;

	@Mock
	public ReimbursementDao rDao;

	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	private Reimbursement r1 = new Reimbursement(1, 100.0d, new Date(1615133769896l), (Date) null, "This is a test 1",
			1, -1, ReimbursementStatus.PENDING, ReimbursementType.FOOD, null, null, null, null);
	private Reimbursement r2 = new Reimbursement(2, 200.0d, new Date(1612802770000l), new Date(1615221970000l),
			"This is a test 2", 1, 2, ReimbursementStatus.APPROVED, ReimbursementType.LODGING, null, null, null, null);
	private Reimbursement r3 = new Reimbursement(3, 200.0d, new Date(1612802770000l), new Date(1615221970000l),
			"This is a test 3", 2, 2, ReimbursementStatus.DENIED, ReimbursementType.TRAVEL, null, null, null, null);

	private ArrayList<Reimbursement> rList = new ArrayList<>();

	@Test
	public void testGetReimbursementById() {

		doReturn(r1).when(rDao).getReimbursementById(1);

		Reimbursement got = rServ.getReimbursementById(1);

		assertEquals(r1.getReId(), got.getReId());
	}

	@Test
	public void testGetAllReimbursements() {
		rList.add(r1);
		rList.add(r2);
		rList.add(r3);

		System.out.println(rList.size());

		doReturn(rList).when(rDao).gettAllReimbursementWithAllInfo();

		ArrayList<Reimbursement> ret = rServ.getAllReimbursements();
		assertEquals(3, ret.size());
	}

	@Test
	public void testGetAllReimbursementsById() {

		rList.add(r1);
		rList.add(r2);
		// rList.add(r3);

		doReturn(rList).when(rDao).getReimbursementsByEmployee(1);

		ArrayList<Reimbursement> ret = rServ.getAllReimbursementsById(1);

		assertEquals(2, ret.size());
	}

	@Test
	public void testApproveReimbursement() {

		r1.setReStatus(ReimbursementStatus.APPROVED);
		r1.setReResolver(2);
		r1.setReResolved(new Date(1615670941039l));

		doReturn(r1).when(rDao).updateReimbursement(r1, 1);

		Reimbursement updated = rServ.approveReimbursement(r1, 2, new Date(1615670941039l));

		assertEquals(r1.getReResolver(), updated.getReResolver());
		assertEquals(ReimbursementStatus.APPROVED, updated.getReStatus());

	}

	@Test
	public void testDenyReimbursement() {

		r1.setReStatus(ReimbursementStatus.DENIED);
		r1.setReResolver(2);
		r1.setReResolved(new Date(1615670941039l));

		doReturn(r1).when(rDao).updateReimbursement(r1, 1);

		Reimbursement updated = rServ.denyReimbursement(r1, 2, new Date(1615670941039l));

		assertEquals(r1.getReResolver(), updated.getReResolver());
		assertEquals(ReimbursementStatus.DENIED, updated.getReStatus());
	}
	
	@Test
	public void testSubmitNewReimbursement() {
		
		
		
		when(rDao.addReimbursement((Reimbursement) any(Class.class))).thenReturn(true);
		
		boolean saved = rServ.sumbitNewReimbursement(0, new Date(1615670941039l), ReimbursementType.OTHER, "Description", 1);
		
		assertTrue(saved);
		
	}
	
	@Test
	public void testGetReimbursementsByStatus() {
		
		rList.add(r1);
		
		doReturn(rList).when(rDao).getReimbursementsByStatus(ReimbursementStatus.PENDING);
		
		ArrayList<Reimbursement> ret = rServ.getReibursementsByStatus(ReimbursementStatus.PENDING);
		
		assertEquals(1, ret.size());
		
	}

}
