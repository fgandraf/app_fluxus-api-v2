package com.felipegandra.app_fluxusapiv2.modules.orders;

import com.felipegandra.app_fluxusapiv2.exceptions.*;
import com.felipegandra.app_fluxusapiv2.modules.branches.BranchRepository;
import com.felipegandra.app_fluxusapiv2.modules.invoices.InvoiceRepository;
import com.felipegandra.app_fluxusapiv2.modules.orders.dtos.*;
import com.felipegandra.app_fluxusapiv2.modules.orders.enums.Status;
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

    public List<OrderFiltered> findFiltered(String filter){

        var filters = filter.split(",");
        var professional = filters[0];
        var service = filters[1];
        var city = filters[2];
        var status = Integer.parseInt(filters[3]);
        var invoiced = Integer.parseInt(filters[4]);
        List<OrderFiltered> orders = new ArrayList<>();

        orderRepository.findFiltered(professional, service, city, status, invoiced)
                .forEach(result -> {
                    var idResult = (Long) result[0];
                    var statusResult = (int) result[1];
                    var professionalResult = (String) result[2];
                    var orderDateResult = ((Timestamp) result[3]).toLocalDateTime().toLocalDate();
                    var referenceCodeResult = (String) result[4];
                    var serviceResult = (String) result[5];
                    var cityResult = (String) result[6];
                    var customerNameResult = (String) result[7];
                    var surveyDateResult = ((Timestamp) result[8]).toLocalDateTime().toLocalDate();
                    var doneDateResult = ((Timestamp) result[9]).toLocalDateTime().toLocalDate();
                    var invoicedResult = (int) result[10];

                    orders.add(new OrderFiltered(
                            idResult,
                            statusResult,
                            professionalResult,
                            orderDateResult,
                            referenceCodeResult,
                            serviceResult,
                            cityResult,
                            customerNameResult,
                            surveyDateResult,
                            doneDateResult,
                            invoicedResult > 0
                            )
                    );
                });

        return orders;
    }

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
        return orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
    }

    public Order create(OrderCreateInput model) {

        var branch = branchRepository.findById(model.branchId()).orElseThrow(() -> new BranchNotFoundException(model.branchId()));
        var professional = professionalRepository.findById(model.professionalId()).orElseThrow(() -> new ProfessionalNotFoundException(model.professionalId()));
        var service = serviceRepository.findById(model.serviceId()).orElseThrow(() -> new ServiceNotFoundException(model.professionalId()));

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

    public Order update(OrderUpdateInput order){

        var branch = branchRepository.findById(order.branchId()).orElseThrow(() -> new BranchNotFoundException(order.branchId()));
        var service = serviceRepository.findById(order.serviceId()).orElseThrow(() -> new ServiceNotFoundException(order.professionalId()));
        var professional = professionalRepository.findById(order.professionalId()).orElseThrow(() -> new ProfessionalNotFoundException(order.professionalId()));
        var invoice = invoiceRepository.findById(order.invoiceId()).orElseThrow(() -> new InvoiceNotFoundException(order.invoiceId()));

        return orderRepository
                .findById(order.orderId())
                .map(foundOrder -> {
                    foundOrder.setReferenceCode(order.referenceCode());
                    foundOrder.setBranch(branch);
                    foundOrder.setOrderDate(order.orderDate());
                    foundOrder.setDeadline(order.deadline());
                    foundOrder.setProfessional(professional);
                    foundOrder.setService(service);
                    foundOrder.setServiceAmount(order.serviceAmount());
                    foundOrder.setMileageAllowance(order.mileageAllowance());
                    foundOrder.setSiopi(order.siopi());
                    foundOrder.setCustomerName(order.customerName());
                    foundOrder.setCity(order.city());
                    foundOrder.setContactName(order.contactName());
                    foundOrder.setContactPhone(order.contactPhone());
                    foundOrder.setCoordinates(order.coordinates());
                    foundOrder.setStatus(Status.fromInt(order.status()));
                    foundOrder.setPendingDate(order.pendingDate());
                    foundOrder.setSurveyDate(order.surveyDate());
                    foundOrder.setDoneDate(order.doneDate());
                    foundOrder.setInvoiced(order.invoiced());
                    foundOrder.setInvoice(invoice);

                    return orderRepository.save(foundOrder);
                })
                .orElseThrow(() ->
                        new OrderNotFoundException(order.orderId())
                );

    }

    public void updateToInvoice(Long invoiceId, List<Long> orders){
        var invoice = invoiceRepository.findById(invoiceId).orElseThrow(() -> new InvoiceNotFoundException(invoiceId));

        for(var item : orders){
            var order = orderRepository.findById(item).orElseThrow(() -> new OrderNotFoundException(item));
            order.setInvoice(invoice);
            order.setInvoiced(true);
            orderRepository.save(order);
        }
    }

    public void updateStatus(Long orderId, int status){
        var order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
        order.setStatus(Status.fromInt(status));
        orderRepository.save(order);
    }

    public void delete(Long id) {
        var branch = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
        orderRepository.delete(branch);
    }
}
