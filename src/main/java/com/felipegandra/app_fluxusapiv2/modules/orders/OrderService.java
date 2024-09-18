package com.felipegandra.app_fluxusapiv2.modules.orders;

import com.felipegandra.app_fluxusapiv2.exceptions.*;
import com.felipegandra.app_fluxusapiv2.modules.branches.BranchRepository;
import com.felipegandra.app_fluxusapiv2.modules.invoices.InvoiceRepository;
import com.felipegandra.app_fluxusapiv2.modules.orders.dtos.*;
import com.felipegandra.app_fluxusapiv2.modules.orders.enums.Status;
import com.felipegandra.app_fluxusapiv2.modules.professionals.ProfessionalRepository;
import com.felipegandra.app_fluxusapiv2.modules.services.ServiceRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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

    public List<OrderFlowResponse> getOrdersFlow(){
        var resultsRaw = orderRepository.findOrdersFlow().orElseThrow(() -> new NoRecordsFoundException());
        List<OrderFlowResponse> orders = new ArrayList<>();

        try{
            resultsRaw.forEach(result -> {
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

                orders.add(new OrderFlowResponse(id, card,status,professionalId));
            });

            return orders;

        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro inesperado.", ex);
        }
    }

    public List<String> getDistinctsCities(){
        var resultRaw = orderRepository.findCities().orElseThrow(() -> new NoRecordsFoundException());
        List<String> cities = new ArrayList<>();

        try {
            resultRaw.forEach(result -> cities.add((String) result[0]));
            return cities;

        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro inesperado.", ex);
        }
    }

    public List<OrderDoneToInvoiceResponse> getOrdersDoneToInvoice(){
        var resultsRaw = orderRepository.findDoneToInvoice().orElseThrow(() -> new NoRecordsFoundException());
        List<OrderDoneToInvoiceResponse> orders = new ArrayList<>();

        try {
            resultsRaw.forEach(result -> {
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

                orders.add(new OrderDoneToInvoiceResponse(
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

        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro inesperado.", ex);
        }
    }

    public List<OrderFilteredResponse> getOrdersFiltered(String filter){
        List<OrderFilteredResponse> orders = new ArrayList<>();

        try {
            var filters = filter.split(",");
            var professional = filters[0];
            var service = filters[1];
            var city = filters[2];
            var status = Integer.parseInt(filters[3]);
            var invoiced = Integer.parseInt(filters[4]);

            var resultsRaw = orderRepository.findFiltered(professional, service, city, status, invoiced).orElseThrow(() -> new NoRecordsFoundException());

            resultsRaw.forEach(result -> {
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

                orders.add(new OrderFilteredResponse(
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

        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro inesperado.", ex);
        }

        return orders;
    }

    public List<OrderInvoicedResponse> getOrdersInvoiced(Long invoiceId){
        var resultsRaw = orderRepository.findInvoiced(invoiceId).orElseThrow(() -> new NoRecordsFoundException());
        List<OrderInvoicedResponse> orders = new ArrayList<>();

        try {
            resultsRaw.forEach(result -> {
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

                orders.add(new OrderInvoicedResponse(
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

        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro inesperado.", ex);
        }

        return orders;
    }

    public List<ProfessionalNameIdResponse> getOrderProfessionalByInvoice(Long invoiceId){
        var resultsRaw = orderRepository.findProfessional(invoiceId).orElseThrow(() -> new NoRecordsFoundException());
        List<ProfessionalNameIdResponse> professionals = new ArrayList<>();

        try {
            resultsRaw.forEach(result -> {
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

                professionals.add(new ProfessionalNameIdResponse(professionalId, nameId));
            });

            return professionals;

        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro inesperado.", ex);
        }
    }

    public OrderResponse findById(Long id) {
        var order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
        return new OrderResponse(order);
    }

    public OrderResponse create(OrderCreateRequest request) {
        var branch = branchRepository.findById(request.branchId()).orElseThrow(() -> new BranchNotFoundException(request.branchId()));
        var professional = professionalRepository.findById(request.professionalId()).orElseThrow(() -> new ProfessionalNotFoundException(request.professionalId()));
        var service = serviceRepository.findById(request.serviceId()).orElseThrow(() -> new ServiceNotFoundException(request.professionalId()));

        try {
            var order = new Order(
                    null,
                    request.referenceCode(),
                    branch,
                    request.orderDate(),
                    request.deadline(),
                    professional,
                    service,
                    request.serviceAmount(),
                    request.mileageAllowance(),
                    request.siopi(),
                    request.customerName(),
                    request.city(),
                    request.contactName(),
                    request.contactPhone(),
                    request.coordinates(),
                    Status.fromInt(request.status()),
                    request.pendingDate(),
                    request.surveyDate(),
                    request.doneDate(),
                    request.comments(),
                    false,
                    null
            );

            var savedOrder = orderRepository.save(order);
            return new OrderResponse(savedOrder);

        }catch (DataIntegrityViolationException ex) {
            throw new DatabaseOperationException("Erro ao salvar: dados inválidos ou em conflito.", ex);
        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro inesperado ao salvar.", ex);
        }
    }

    public OrderResponse update(OrderUpdateRequest request){
        var order = orderRepository.findById(request.id()).orElseThrow(() -> new OrderNotFoundException(request.id()));
        var branch = branchRepository.findById(request.branchId()).orElseThrow(() -> new BranchNotFoundException(request.branchId()));
        var service = serviceRepository.findById(request.serviceId()).orElseThrow(() -> new ServiceNotFoundException(request.professionalId()));
        var professional = professionalRepository.findById(request.professionalId()).orElseThrow(() -> new ProfessionalNotFoundException(request.professionalId()));
        var invoice = invoiceRepository.findById(request.invoiceId()).orElseThrow(() -> new InvoiceNotFoundException(request.invoiceId()));

        try {
            order.setReferenceCode(request.referenceCode());
            order.setBranch(branch);
            order.setOrderDate(request.orderDate());
            order.setDeadline(request.deadline());
            order.setProfessional(professional);
            order.setService(service);
            order.setServiceAmount(request.serviceAmount());
            order.setMileageAllowance(request.mileageAllowance());
            order.setSiopi(request.siopi());
            order.setCustomerName(request.customerName());
            order.setCity(request.city());
            order.setContactName(request.contactName());
            order.setContactPhone(request.contactPhone());
            order.setCoordinates(request.coordinates());
            order.setStatus(Status.fromInt(request.status()));
            order.setPendingDate(request.pendingDate());
            order.setSurveyDate(request.surveyDate());
            order.setDoneDate(request.doneDate());
            order.setInvoiced(request.invoiced());
            order.setInvoice(invoice);

            var savedOrder = orderRepository.save(order);
            return new OrderResponse(savedOrder);

        }catch (DataIntegrityViolationException ex) {
            throw new DatabaseOperationException("Erro ao salvar: dados inválidos ou em conflito.", ex);
        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro inesperado ao salvar.", ex);
        }
    }

    public void updateToInvoice(Long invoiceId, List<Long> orders){
        var invoice = invoiceRepository.findById(invoiceId).orElseThrow(() -> new InvoiceNotFoundException(invoiceId));

        try{
            for(var item : orders){
                var order = orderRepository.findById(item).orElseThrow(() -> new OrderNotFoundException(item));

                order.setInvoice(invoice);
                order.setInvoiced(true);

                orderRepository.save(order);
            }

        }catch (DataIntegrityViolationException ex) {
            throw new DatabaseOperationException("Erro ao salvar: dados inválidos ou em conflito.", ex);
        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro inesperado ao salvar.", ex);
        }
    }

    public void updateStatus(Long orderId, int status){
        var order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));

        try {
            order.setStatus(Status.fromInt(status));
            orderRepository.save(order);
        }catch (DataIntegrityViolationException ex) {
            throw new DatabaseOperationException("Erro ao salvar: dados inválidos ou em conflito.", ex);
        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro inesperado ao salvar.", ex);
        }
    }

    public void delete(Long id) {
        var order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));

        try {
            orderRepository.delete(order);
        } catch (DataIntegrityViolationException ex) {
            throw new DatabaseOperationException("Erro ao deletar: pode haver dados relacionados.", ex);
        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro inesperado ao deletar.", ex);
        }
    }

}