package db.view;

import db.table.LeaseRequestTable;
import db.table.LeaseTable;
import db.table.LeaseUtils;
import pojo.*;
import util.DBAccessor;
import util.RoommateFinder;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Nikhil
 * Date: 02-04-15
 */
public class LeaseView extends View {

    public static final String VIEW_NAME = "LEASE_VIEW";
    public static final String HOUSING_NAME = "housing_name";
    public static final String HOUSING_TYPE = "housing_type";

    @Override
    public String getViewName() {
        return VIEW_NAME;
    }

    /*LEASE_NUMBER + " " + ColumnTypes.ID_INT_TYPE + "," +
    LeaseRequestTable.REQUEST_NUMBER + " " + ColumnTypes.ID_INT_TYPE + " not null," +
    START_DATE + " " + ColumnTypes.DATE_TYPE + " not null," +
    END_DATE + " " + ColumnTypes.DATE_TYPE + " not null," +
    HOUSING_ID + " " + ColumnTypes.ID_TYPE + " not null," +
    LOCATION_NUMBER + " " + ColumnTypes.VARCHAR2_SIZE_20_TYPE + " not null," +*/

    /*RES_ID + " " + ColumnTypes.ID_TYPE + " not null," +
    STATUS + " " + ColumnTypes.VARCHAR2_SIZE_20_TYPE + " not null," +
    ENTER_DATE + " " + ColumnTypes.DATE_TYPE + " not null," +
    DURATION + " " + ColumnTypes.INTEGER_TYPE + " not null," +
    PAYMENT_OPTION + " " + ColumnTypes.VARCHAR2_SIZE_20_TYPE + " not null," +
    USE_PRIVATE_ACCOMMODATION + " " + ColumnTypes.BOOLEAN_TYPE + " default '0' not null," +
    UPDATED_BY + " " + ColumnTypes.ID_TYPE + "," +
    UPDATED_ON + " " + ColumnTypes.DATE_TYPE + " ," +*/

    @Override
    public void createView(Connection conn) throws SQLException {
        String query = "CREATE VIEW " + getViewName() + " as " +
                " SELECT l." + LeaseTable.LEASE_NUMBER + ", l." + LeaseRequestTable.REQUEST_NUMBER + ", l."
                + LeaseTable.START_DATE + ", l." + LeaseTable.END_DATE + ", l." + LeaseTable.HOUSING_ID + ", l." + LeaseTable.LOCATION_NUMBER
                + ", lr." + LeaseRequestTable.RES_ID + ", lr." + LeaseRequestTable.STATUS + ", lr." + LeaseRequestTable.ENTER_DATE
                + ", lr." + LeaseRequestTable.DURATION + ", lr." + LeaseRequestTable.PAYMENT_OPTION + ", lr." + LeaseRequestTable.USE_PRIVATE_ACCOMMODATION
                + ", lr." + LeaseRequestTable.UPDATED_BY + ", lr." + LeaseRequestTable.UPDATED_ON +
                ", h.name as " + HOUSING_NAME + ", h.type as " + HOUSING_TYPE +
                " FROM " + LeaseTable.TABLE_NAME + " l inner join " + LeaseRequestTable.TABLE_NAME
                + " lr on l." + LeaseRequestTable.REQUEST_NUMBER + " = lr." + LeaseRequestTable.REQUEST_NUMBER
                + " left outer join housing h on h.housing_id = l.housing_id";
        DBAccessor.executeQuery(conn, query);
    }

    public List<Lease> viewFormerLeases(Connection conn, String residentId) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM ").append(getViewName()).append(" ");
        query.append("where ");
        //query.append(LeaseTable.RES_ID).append(" = '").append(residentId).append("'");
        query.append(" and ");
        //query.append(LeaseTable.STATUS).append(" = '").append(LeaseTable.RequestStatus.Completed).append("'");

        return getLeases(conn, query.toString());
    }



    public Lease viewCurrentLease(Connection conn, String residentId) {
        String query = "SELECT * FROM " + getViewName() + " where res_id = '" + residentId + "' and "
                + LeaseRequestTable.STATUS + " = '" + LeaseTable.RequestStatus.InProgress + "'";
        List<Lease> leases = getLeases(conn, query);
        if (leases.isEmpty()) {
            return null;
        } else {
            return leases.get(0);
        }
    }



    public Lease viewLease(Connection conn, int leaseNumber) {
        String query = "SELECT * FROM " + getViewName() + " where " + LeaseTable.LEASE_NUMBER + " = " + leaseNumber + "";
        List<Lease> leases = getLeases(conn, query);
        return leases.isEmpty() ? null : leases.get(0);
    }

    public Lease viewLeaseFromLeaseRequest(Connection conn, int requestNumber) {
        String query = "SELECT * FROM " + getViewName() + " where " + LeaseRequestTable.REQUEST_NUMBER + " = " + requestNumber + "";
        List<Lease> leases = getLeases(conn, query);
        return leases.isEmpty() ? null : leases.get(0);
    }

    public List<Lease> viewAllLeaseRequestsForResident(Connection conn, String residentId) {
        String query = "SELECT * FROM " + getViewName() + " where res_id = '" + residentId + "'";
        return getLeases(conn, query);
    }

    public ProposedHousing getProposedHousingForLease(Connection conn, LeaseRequest leaseRequest) {
        ProposedHousing proposedHousing = new ProposedHousing();
        System.out.println("Lease Request For Testing " + leaseRequest);
        if(leaseRequest.isUsePrivateAccommodation()){

            // TODO add freshman check
            proposedHousing.setUsePrivateAccommodation(true);
            Housing housing= LeaseUtils.getHousingDetail(conn, null);
            proposedHousing.setProposedHousingId(housing.getHousingId());
            proposedHousing.setProposedHousingName(housing.getName());
            proposedHousing.setProposedLocationNumber(housing.getLocationNumber());
            proposedHousing.setProposedHousingType(housing.getType());
            return proposedHousing;
        }

        Housing preferredHouse = LeaseUtils.getHousingDetail(conn, leaseRequest.getPreference1());
        if(preferredHouse != null){
            return getProposedHousing(proposedHousing, preferredHouse);
        }

        preferredHouse = LeaseUtils.getHousingDetail(conn, leaseRequest.getPreference2());
        if(preferredHouse != null){
            return getProposedHousing(proposedHousing, preferredHouse);
        }

        preferredHouse = LeaseUtils.getHousingDetail(conn, leaseRequest.getPreference3());
        if(preferredHouse != null){
            return getProposedHousing(proposedHousing, preferredHouse);
        }

        return null;
    }

    private ProposedHousing getProposedHousing(ProposedHousing proposedHousing, Housing preferredHouse) {
        proposedHousing.setProposedHousingId(preferredHouse.getHousingId());
        proposedHousing.setProposedHousingName(preferredHouse.getName());
        proposedHousing.setProposedHousingType(preferredHouse.getType());
        proposedHousing.setProposedLocationNumber(preferredHouse.getLocationNumber());
        return proposedHousing;
    }

    public ProposedHousing getProposedHousingForPreference(Connection conn, LeasePreference preference) {


        /*LeasePreference preference = lease.getPreference1();
        if (preference != null) {
            if (LeasePreferenceTable.PreferenceType.Hall.name().equals(preference.getType())) {

            } else if (LeasePreferenceTable.PreferenceType.StudentApartment.name().equals(preference.getType())) {

            } else {

            }
        }
        return true;*/
        return null;
    }


    private List<Lease> getLeases(Connection conn, String query) {
        List<Lease> leases = new ArrayList<Lease>();
        try (ResultSet rs = DBAccessor.selectQuery(conn, query)) {
            while (rs.next()) {
                leases.add(getLease(rs));
            }
        } catch (SQLException ex) {
            System.err.println("Error Occurred During View Lease " + ex.getMessage());
        }
        return leases;
    }

    public static Lease getLease(ResultSet rs) throws SQLException {
        Lease lease = new Lease();
        lease.setLeaseNumber(rs.getInt(LeaseTable.LEASE_NUMBER));
        lease.setStatus(rs.getString(LeaseRequestTable.STATUS));
        lease.setStartDate(rs.getDate(LeaseTable.START_DATE));
        lease.setEndDate(rs.getDate(LeaseTable.END_DATE));
        lease.setDuration(rs.getInt(LeaseRequestTable.DURATION));
        lease.setPaymentOption(rs.getString(LeaseRequestTable.PAYMENT_OPTION));
        lease.setLocationNumber(rs.getString(LeaseTable.LOCATION_NUMBER));
        lease.setHousingId(rs.getString(LeaseTable.HOUSING_ID));
        lease.setHousingId(rs.getString(LeaseTable.HOUSING_ID));
        lease.setHousingName(rs.getString(HOUSING_NAME));
        lease.setHousingType(rs.getString(HOUSING_TYPE));
        lease.setLeaseRequest(LeaseRequestView.getLeaseRequest(rs));
        return lease;
    }

    public List<Lease> viewOpenLeaseRequests(Connection conn) {
        String query = "SELECT * FROM " + getViewName() + " where " + LeaseRequestTable.STATUS + " IN ('"
                + LeaseTable.RequestStatus.Pending.name() + "', '" + LeaseTable.RequestStatus.WaitList.name() +"')";
        System.out.println(query);
        return getLeases(conn, query);
    }

    public List<PotentialRoommate> getPotentialRoommates(Connection conn, int requestNumber) {
        List<PotentialRoommate> potentialRoommates = null;

        String query = "SELECT res_id from LEASE_REQUEST WHERE REQUEST_NUMBER = "+requestNumber;
        String residentId = "";
        try {
            ResultSet resultSet = DBAccessor.selectQuery(conn,query);
            while(resultSet.next()){
                residentId = resultSet.getString("res_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        RoommateFinder roommateFinder = new RoommateFinder();
        List<Resident> residents = roommateFinder.findAllMatchRoommates(conn,residentId);
        if(residents == null)
            return null;
        potentialRoommates = new ArrayList<>();
        for (Resident resident : residents){
            PotentialRoommate potentialRoommate = new PotentialRoommate();
            potentialRoommate.setResidentId(resident.getResId());
            potentialRoommate.setFname(resident.getFname());
            potentialRoommate.setLname(resident.getLname());
            potentialRoommate.setCategory(resident.getCategory());
            potentialRoommate.setGender(resident.getGender());
            potentialRoommate.setSmoker(resident.getIsSmoker());
            potentialRoommates.add(potentialRoommate);
        }
        return potentialRoommates;
    }
}
