package com.felipegandra.app_fluxusapiv2.modules.orders;

import com.felipegandra.app_fluxusapiv2.exceptions.NotFoundException;
import com.felipegandra.app_fluxusapiv2.modules.orders.dtos.*;
import com.felipegandra.app_fluxusapiv2.modules.orders.enums.Status;
import com.felipegandra.app_fluxusapiv2.modules.branches.BranchRepository;
import com.felipegandra.app_fluxusapiv2.modules.invoices.InvoiceRepository;
import com.felipegandra.app_fluxusapiv2.modules.professionals.ProfessionalRepository;
import com.felipegandra.app_fluxusapiv2.modules.services.ServiceRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final BranchRepository branchRepository;
    private final ProfessionalRepository professionalRepository;
    private final ServiceRepository serviceRepository;
    private final InvoiceRepository invoiceRepository;

    public OrderService(OrderRepository orderRepository, BranchRepository branchRepository, ProfessionalRepository professionalRepository, ServiceRepository serviceRepository, InvoiceRepository invoiceRepository) {
        this.branchRepository = branchRepository;
        this.orderRepository = orderRepository;
        this.professionalRepository = professionalRepository;
        this.serviceRepository = serviceRepository;
        this.invoiceRepository = invoiceRepository;
    }

    public List<OrderFlowOutput> findFlow(){

        List<OrderFlowOutput> orders = new ArrayList<>();

        orderRepository.findOrdersFlow()
                .forEach(result -> {
                    var id = (Long) result[0];
                    var status = (int) result[1];
                    var professionalId = (Long) result[2];
                    var city = (String) result[3];
                    var referenceCode = ((String) result[4]).substring(9, 18).replaceFirst("^0+(?!$)", "");
                    var customerName = (String) result[5];
                    var deadline = ((Timestamp) result[6]).toLocalDateTime().toLocalDate();
                    var tag = (String) result[7];

                    var card = new String[3];
                    card[0] = tag + "-" + city + "-" + referenceCode;
                    card[1] = customerName;
                    card[2] = "- Prazo: " + deadline.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                    orders.add(new OrderFlowOutput(id, card,status,professionalId));
                });

        return orders;
    }

    public List<String> findCities(){
        return orderRepository.findCities().stream().map(result -> (String) result[0]).collect(Collectors.toList());

    }

    public List<OrderDoneToInvoice> findDoneToInvoice(){

        List<OrderDoneToInvoice> orders = new ArrayList<>();

        orderRepository.findDoneToInvoice()
                .forEach(result -> {
                    var id = (Long) result[0];
                    var orderDate = ((Timestamp) result[1]).toLocalDateTime().toLocalDate();
                    var referenceCode = (String) result[2];
                    var professional = (String) result[3];
                    var service = (String) result[4];
                    var city = (String) result[5];
                    var customerName = (String) result[6];
                    var surveyDate = ((Timestamp) result[7]).toLocalDateTime().toLocalDate();
                    var doneDate = ((Timestamp) result[8]).toLocalDateTime().toLocalDate();
                    var serviceAmount = ((BigDecimal) result[9]).doubleValue();
                    var mileageAllowance = ((BigDecimal) result[10]).doubleValue();

                    orders.add(new OrderDoneToInvoice(
                            id,
                            orderDate,
                            referenceCode,
                            professional,
                            service,
                            city,
                            customerName,
                            surveyDate,
                            doneDate,
                            serviceAmount,
                            mileageAllowance
                            )
                    );
                });

        return orders;
    }





    //TO DO:
    // getFiltered





    public List<OrderInvoiced> findInvoiced(Long invoiceId){

        List<OrderInvoiced> orders = new ArrayList<>();

        orderRepository.findInvoiced(invoiceId)
                .forEach(result -> {
                    var id = (Long) result[0];
                    var orderDate = ((Timestamp) result[1]).toLocalDateTime().toLocalDate();
                    var referenceCode = (String) result[2];
                    var professionalId = (Long) result[3];
                    var professional = (String) result[4];
                    var service = (String) result[5];
                    var city = (String) result[6];
                    var customerName = (String) result[7];
                    var surveyDate = ((Timestamp) result[8]).toLocalDateTime().toLocalDate();
                    var doneDate = ((Timestamp) result[9]).toLocalDateTime().toLocalDate();
                    var invoiceIdResult = (Long) result[10];
                    var serviceAmount = ((BigDecimal) result[11]).doubleValue();
                    var mileageAllowance = ((BigDecimal) result[12]).doubleValue();

                    orders.add(new OrderInvoiced(
                                    id,
                                    orderDate,
                                    referenceCode,
                                    professionalId,
                                    professional,
                                    service,
                                    city,
                                    customerName,
                                    surveyDate,
                                    doneDate,
                                    invoiceIdResult,
                                    serviceAmount,
                                    mileageAllowance
                            )
                    );
                });

        return orders;
    }

    public List<ProfessionalNameId> findProfessional(Long invoiceId){

        List<ProfessionalNameId> professionals = new ArrayList<>();

        orderRepository.findProfessional(invoiceId)
                .forEach(result -> {
                    var professionalId = (Long) result[0];
                    var profession = (String) result[1];
                    var name = (String) result[2];

                    var nameId = profession != null ? profession.substring(0, 3) + ". " : "";
                    String[] nameParts = name.split(" ");
                    if (nameParts.length > 1) {
                        nameId += nameParts[0] + " " + nameParts[nameParts.length - 1];
                    } else {
                        nameId += name;
                    }

                    professionals.add(new ProfessionalNameId(professionalId, nameId));
                });

        return professionals;
    }

    public Order findById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Order", id));
    }

    public Order create(OrderCreateInput model) {

        var branch = branchRepository.findById(model.branchId()).orElseThrow(() -> new NotFoundException("Branch", model.branchId()));
        var professional = professionalRepository.findById(model.professionalId()).orElseThrow(() -> new NotFoundException("Professional", model.professionalId()));
        var service = serviceRepository.findById(model.serviceId()).orElseThrow(() -> new NotFoundException("Service", model.professionalId()));

        var order = new Order();
        order.setReferenceCode(model.referenceCode());
        order.setBranch(branch);
        order.setOrderDate(model.orderDate());
        order.setDeadline(model.deadline());
        order.setProfessional(professional);
        order.setService(service);
        order.setServiceAmount(model.serviceAmount());
        order.setMileageAllowance(model.mileageAllowance());
        order.setSiopi(model.siopi());
        order.setCustomerName(model.customerName());
        order.setCity(model.city());
        order.setContactName(model.contactName());
        order.setContactPhone(model.contactPhone());
        order.setCoordinates(model.coordinates());
        order.setStatus(Status.fromInt(model.status()));
        order.setPendingDate(model.pendingDate());
        order.setSurveyDate(model.surveyDate());
        order.setDoneDate(model.doneDate());
        order.setComments(model.comments());
        order.setInvoiced(false);
        order.setInvoice(null);

        return orderRepository.save(order);
    }

    public Order update(Order order){
        return orderRepository
                .findById(order.getId())
                .map(foundOrder -> {
                    foundOrder.setReferenceCode(order.getReferenceCode());
                    foundOrder.setBranch(order.getBranch());
                    foundOrder.setOrderDate(order.getOrderDate());
                    foundOrder.setDeadline(order.getDeadline());
                    foundOrder.setProfessional(order.getProfessional());
                    foundOrder.setService(order.getService());
                    foundOrder.setServiceAmount(order.getServiceAmount());
                    foundOrder.setMileageAllowance(order.getMileageAllowance());
                    foundOrder.setSiopi(order.getSiopi());
                    foundOrder.setCustomerName(order.getCustomerName());
                    foundOrder.setCity(order.getCity());
                    foundOrder.setContactName(order.getContactName());
                    foundOrder.setContactPhone(order.getContactPhone());
                    foundOrder.setCoordinates(order.getCoordinates());
                    foundOrder.setStatus(order.getStatus());
                    foundOrder.setPendingDate(order.getPendingDate());
                    foundOrder.setSurveyDate(order.getSurveyDate());
                    foundOrder.setDoneDate(order.getDoneDate());
                    foundOrder.setInvoiced(order.getInvoiced());
                    foundOrder.setInvoice(order.getInvoice());
                    return orderRepository.save(foundOrder);
                })
                .orElseThrow(() ->
                        new NotFoundException("Order", order.getId())
                );
    }

    public void updateToInvoice(Long invoiceId, List<Long> orders){
        var invoice = invoiceRepository.findById(invoiceId).orElseThrow(() -> new NotFoundException("Invoice", invoiceId));

        for(var item : orders){
            var order = orderRepository.findById(item).orElseThrow(() -> new NotFoundException("Order", item));
            order.setInvoice(invoice);
            order.setInvoiced(true);
            orderRepository.save(order);
        }
    }

    public void updateStatus(Long orderId, int status){
        var order = orderRepository.findById(orderId).orElseThrow(() -> new NotFoundException("Order", orderId));
        order.setStatus(Status.fromInt(status));
        orderRepository.save(order);
    }

    public void delete(Long id) {
        var branch = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Order", id));
        orderRepository.delete(branch);
    }
}
