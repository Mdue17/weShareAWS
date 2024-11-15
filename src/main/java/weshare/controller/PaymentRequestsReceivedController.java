package weshare.controller;

import io.javalin.http.Handler;
import weshare.model.*;
import weshare.persistence.ExpenseDAO;
import weshare.server.Routes;
import weshare.server.ServiceRegistry;
import weshare.server.WeShareServer;

import java.util.*;
import javax.money.MonetaryAmount;

public class PaymentRequestsReceivedController {

    public static final Handler paymentRequestsReceivedPage = context -> {
        ExpenseDAO expensesDAO = ServiceRegistry.lookup(ExpenseDAO.class);
        Person personLoggedIn = WeShareServer.getPersonLoggedIn(context);

        Collection<PaymentRequest> paymentRequestsReceived = expensesDAO.findPaymentRequestsReceived(personLoggedIn);
        MonetaryAmount totalAmount = MoneyHelper.amountOf(0);

        for (PaymentRequest paymentRequest : paymentRequestsReceived) {
            totalAmount = totalAmount.add(paymentRequest.getAmountToPay());
        }

        Map<String, Object> viewModel = new HashMap<>();
        viewModel.put("paymentRequest", paymentRequestsReceived);
        viewModel.put("total", totalAmount);

        context.render("paymentrequests_received.html", viewModel);
    };

    public static final Handler payPaymentRequest = context -> {
        // Handler code for paying a payment request
        // ...

        context.redirect(Routes.PAYMENT_REQUESTS_RECEIVED);
    };

    public static final Handler addPaymentRequest = context -> {
        // Handler code for adding a payment request
        // ...

        context.redirect(Routes.EXPENSES); // Redirect to the expenses page or a suitable destination.
    };

    // Additional handlers and logic can be added here as needed

    // You can also place the tests for this controller within the same class
    public static class PaymentRequestsReceivedControllerTests {
        // Define your tests here
    }
}
