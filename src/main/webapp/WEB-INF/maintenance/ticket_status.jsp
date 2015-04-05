<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="container">

  <div class="upper">
    <span><button type="button" id="backButton" class="btn btn-primary navbar-btn"><span class="glyphicon glyphicon-arrow-left" aria-hidden="true">Back</span></button></span>
    <span class="upper-text" style="margin-left:32%">Ticket status</span>
    <span></span><a id="logout" class="btn btn-primary navbar-btn logout" href="/<s:property value='appName'/>/logout.action"> Log out</a></span>
  </div>
  <label class="statusMessage"><strong><s:property value="message" /></strong></label>

    <s:form action="viewTicketStatus">
        <table class="table table-bordered" data-toggle="table"  data-cache="false" data-height="299">
            <tr>
                <td><b>Ticket type</b></td>
                <td><b>Status</b></td>
                <td><b>Description</b></td>
                <td><b>Date created</b></td>
            </tr>
            <s:iterator value="tickets" status="stat">
                <tr>
                    <td><s:property value="ticket" /></td>
                    <td><s:property value="status" /></td>
                    <td><s:property value="description" /></td>
                    <td><s:property value="date" /></td>
                </tr>
            </s:iterator>
        </table>
    </s:form>

</div>
