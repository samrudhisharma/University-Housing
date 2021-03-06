package db.table;

import pojo.Housing;
import pojo.LeasePreference;
import util.DBAccessor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Author : abhishek
 * Created on 4/8/15.
 */
public class LeaseUtils {


    public static Housing getHousingDetail(Connection conn, LeasePreference preference) {

        Housing housing = null;
        if(preference == null) {
            List<Housing> housingList = LeaseUtils.getAllVacancies(conn);
            housing = housingList.isEmpty() ? null : housingList.get(0);
            System.out.println(" Pointer is here " + housing);
            return housing;
        }

        String residenceType  = preference.getType();
        String hallId = (preference.getHallId() == null) ? null : preference.getHallId();
        String query = "";
        String prefType = residenceType;

        switch (prefType){
            case "Residence Halls":
                query = "SELECT * FROM ROOMS " +
                        " WHERE TYPE = '" +  prefType + "'  AND " +
                        " PARENT_ID IN  " +
                        " (SELECT HOUSING_ID FROM HOUSING WHERE " + hallId + " is null or " + "HOUSING_ID = '"+ hallId + "' ) AND " +
                        " (select count(*) from lease l, LEASE_REQUEST lr" +
                        " where lr.REQUEST_NUMBER = l.REQUEST_NUMBER and lr.status <> 'InProgress' and l.LOCATION_NO = PLACE_NUM) = 0" +
                        " order by PARENT_ID, PLACE_NUM";

                try (ResultSet rs = DBAccessor.selectQuery(conn, query)) {
                    if(rs.next()){
                        housing = new Housing();
                        housing.setHousingId(rs.getString("PARENT_ID"));
                        housing.setLocationNumber(rs.getString("PLACE_NUM"));
                        housing.setName(preference.getHallName());
                        housing.setType(preference.getType());
                    }
                } catch (SQLException ex) {
                    System.err.println("Error Occurred During View Lease " + ex.getMessage());
                }
                break;
            case "General Student Apartments" :
                query = "SELECT * FROM ROOMS " +
                               " WHERE TYPE = '" + prefType + "'  AND " +
                        " (select count(*) from lease l, LEASE_REQUEST lr" +
                        " where lr.REQUEST_NUMBER = l.REQUEST_NUMBER and lr.status <> 'InProgress' and l.LOCATION_NO = PLACE_NUM) = 0" +
                        " order by PARENT_ID, PLACE_NUM";

                try (ResultSet rs = DBAccessor.selectQuery(conn, query)) {
                    if(rs.next()){
                        housing = new Housing();
                        housing.setHousingId(rs.getString("PARENT_ID"));
                        housing.setLocationNumber(rs.getString("PLACE_NUM"));
                        housing.setName(preference.getHallName());
                        housing.setType(preference.getType());
                    }
                } catch (SQLException ex) {
                    System.err.println("Error Occurred During View Lease " + ex.getMessage());
                }
                break;
            case "Family Apartments":

                query = " SELECT F.APT_ID AS APT_ID, H.HOUSING_ID AS H_ID, H.NAME AS NAME " +
                        " FROM FAMILY_APT F, HOUSING H " +
                        " WHERE H.HOUSING_ID = F.F_APT_ID AND " +
                        " (select count(*) from lease l, LEASE_REQUEST lr" +
                        " where lr.REQUEST_NUMBER = l.REQUEST_NUMBER and lr.status <> 'InProgress' and l.LOCATION_NO = APT_ID) = 0" +
                        " order by H_ID, APT_ID";

                try (ResultSet rs = DBAccessor.selectQuery(conn, query)) {
                    if(rs.next()){
                        housing = new Housing();
                        housing.setHousingId(rs.getString("H_ID"));
                        housing.setLocationNumber(rs.getString("APT_ID"));
                        housing.setName(rs.getString("NAME"));
                        housing.setType(preference.getType());
                    }

                } catch (SQLException ex) {
                    System.err.println("Error Occurred During View Lease " + ex.getMessage());
                }
        }
        return housing;
    }

    public static List<Housing> getAllVacancies(Connection conn) {

        List<Housing> vacancies = new LinkedList<>();
        String query = " SELECT R.PARENT_ID AS H_ID, R.APT_ID AS APT_ID, R.TYPE AS TYPE, " +
                         "R.PLACE_NUM AS PLACE_NUM, H.NAME AS NAME " +
                       " FROM ROOMS R, HOUSING H " +
                       " WHERE R.PARENT_ID=H.HOUSING_ID " +
                       " AND R.PLACE_NUM NOT IN (SELECT LOCATION_NO FROM LEASE) ";

        try (ResultSet rs = DBAccessor.selectQuery(conn, query)) {
            while(rs.next()){
                Housing housing = new Housing();
                housing.setHousingId(rs.getString("H_ID"));
                housing.setLocationNumber(rs.getString("PLACE_NUM"));
                housing.setAptId(rs.getString("APT_ID"));
                housing.setName(rs.getString("NAME"));
                housing.setType(rs.getString("TYPE"));
                vacancies.add(housing);
            }

        } catch (SQLException ex) {
            System.err.println("Error Occurred During View Lease " + ex.getMessage());
        }

        query = " SELECT F.APT_ID AS APT_ID, H.HOUSING_ID AS H_ID, H.NAME AS NAME, H.TYPE AS TYPE " +
                    " FROM FAMILY_APT F, HOUSING H " +
                    " WHERE APT_ID NOT IN (SELECT LOCATION_NO FROM LEASE) ";

        try (ResultSet rs = DBAccessor.selectQuery(conn, query)) {
            if(rs.next()){
                Housing housing = new Housing();
                housing.setHousingId(rs.getString("H_ID"));
                housing.setAptId(rs.getString("APT_ID"));
                housing.setName(rs.getString("NAME"));
                housing.setType(rs.getString("TYPE"));
                vacancies.add(housing);
            }
        } catch (SQLException ex) {
             System.err.println("Error Occurred During View Lease " + ex.getMessage());
        }
        return vacancies;

    }

}
