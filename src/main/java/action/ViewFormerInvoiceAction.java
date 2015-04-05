package action;

import db.table.InvoiceTable;
import db.view.InvoiceView;
import pojo.Invoice;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nisarg on 4/3/15.
 */
public class ViewFormerInvoiceAction extends UHAction {
    private List<String> formerInvoices;
    private Invoice invoice;

    public ViewFormerInvoiceAction(){

    }


    public String execute() {

        return SUCCESS;
    }

    public String getLeases() {
        String resident_id = (String) sessionMap.get("username");
        resident_id = resident_id.trim();
        System.out.println(resident_id);
        InvoiceTable invoiceTable = new InvoiceTable();
        System.out.println("created invoice table!");
        formerInvoices = new ArrayList<>();
        System.out.println("now going into invoice table to fetch former invoices --------------------------------");
        formerInvoices = invoiceTable.getFormerInvoices(conn, resident_id);

        return SUCCESS;
    }

    public String submit() {

        String resident_id = (String) sessionMap.get("username");
        resident_id = resident_id.trim();
        InvoiceView invoiceView = new InvoiceView();
        System.out.println(invoice.getInvoiceId());
        invoice = invoiceView.getFormerInvoiceDetails(conn, resident_id, invoice.getInvoiceId());

        return SUCCESS;
    }

    public List<String> getFormerInvoices() {
        return formerInvoices;
    }

    public void setFormerInvoices(List<String> formerInvoices) {
        this.formerInvoices = formerInvoices;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }


}
