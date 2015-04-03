<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="container">

  <div class="upper">
    <span><button type="button" id="backButton" class="btn btn-primary navbar-btn"><span class="glyphicon glyphicon-arrow-left" aria-hidden="true">Back</span></button></span>
    <span class="upper-text" style="margin-left:32%">Parking status Page</span>
    <span></span><a id="logout" class="btn btn-primary navbar-btn logout" href="/<s:property value='appName'/>/logout.action"> Log out</a></span>
  </div>
  <label class="statusMessage"><strong><s:property value="message" /></strong></label>

    <table class="table table-hover" data-toggle="table"  data-cache="false" data-height="299">

      <tbody>
      <tr>
        <th>Vehicle Type:</th>
        <td data-field = "requestStatus"><s:textfield name="parkingRequest.requestStatus" readonly="true"/></td>
      </tr>
      <tr>
        <th>Vehicle Type:</th>
        <td><s:textfield name="parkingRequest.vehicle" readonly="true"/></td>
      </tr>
      <tr>
        <th>Handicapped?:</th>
        <td><s:textfield name= "parkingRequest.handicapped" readonly="true"/></td>
      </tr>
      <tr>
        <th>Nearby Spot?:</th>
        <td><s:textfield name="parkingRequest.nearSpot" readonly="true"/></td>
      </tr>
      </tbody>

    </table>

</div>