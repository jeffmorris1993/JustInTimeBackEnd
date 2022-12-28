package com.justintime.cardetail.Util;

import com.justintime.cardetail.Model.Entity.AddOnEntity;
import com.justintime.cardetail.Model.Entity.BookingEntity;
import com.justintime.cardetail.Model.Entity.VehicleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendEmail(BookingEntity bookingEntity, String notes, List<String> serviceProviders) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(bookingEntity.getCustomer().getEmail());
        message.setSubject("Just-In Time Auto Detailing Receipt");
        String bodyContent = createReceiptContent(bookingEntity, notes, serviceProviders);
        message.setText(bodyContent);
        emailSender.send(message);
    }

    private String createReceiptContent(BookingEntity bookingEntity, String notes, List<String> serviceProviders) {
        StringBuilder content = new StringBuilder("Services Rendered on ");
        content.append(new Date(bookingEntity.getDateOfService().getTime())).append(": \n");
        VehicleEntity vehicleEntity = bookingEntity.getVehicle();
        content.append("Detail Service | ")
                .append(Constants.ServiceType.valueOfId(vehicleEntity.getServiceTypeId()).getLabel())
                .append(" | $")
                .append(determineDisplayCostForService
                        (Constants.ServiceType.valueOfId(vehicleEntity.getServiceTypeId()), vehicleEntity.getModel()))
                .append("\n");
        for (AddOnEntity addOnEntity : vehicleEntity.getAddOnEntities()) {
            content.append("Add On | ")
                    .append(Constants.AddOnType.valueOf(addOnEntity.getAddOnId()).getLabel())
                    .append(" | $")
                    .append(determineDisplayCostForAddOn
                            (Constants.AddOnType.valueOf(addOnEntity.getAddOnId()), vehicleEntity.getModel()))
                    .append("\n");
        }
        content.append("Tip: $").append(bookingEntity.getTip()).append("\n");
        content.append("Total Cost: $").append(bookingEntity.getTotalCost()).append("\n");
        content.append("Service Providers: ").append(serviceProviders.toString()).append("\n");
        content.append("Notes: ").append(notes);
        return content.toString();
    }

    private BigDecimal determineDisplayCostForService(Constants.ServiceType service, String model){
        return model.toLowerCase().contains("car") || model.toLowerCase().contains("suv") ? service.getCarSUVCost()
                : service.getTruckVanCost();
    }

    private BigDecimal determineDisplayCostForAddOn(Constants.AddOnType addOn, String model){
        return model.toLowerCase().contains("car") || model.toLowerCase().contains("suv") ? addOn.getCarSUVCost()
                : addOn.getTruckVanCost();
    }
}
