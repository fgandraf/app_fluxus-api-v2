package com.felipegandra.app_fluxusapiv2.modules.orders;

import com.felipegandra.app_fluxusapiv2.modules.orders.dtos.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("v2/orders")
@Tag(name = "Order Controller", description = "Controladora responsável por gerenciar as ordens de serviços")
public class OrderController {
    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping("/flow")
    @Operation(summary = "Obter cards das ordens de serviço", description = "Retorna os cards de todas as ordem de serviços não faturadas.")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<OrderFlowResponse>> getFlow() {
        List<OrderFlowResponse> response = service.getOrdersFlow();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/cities")
    @Operation(summary = "Obter todas as cidades das ordens de serviço", description = "Retorna a relação de cidades existentes nas ordens de serviços cadastradas.")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<String>> getCities() {
        List<String> response = service.getDistinctsCities();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/done-to-invoice")
    @Operation(summary = "Obter todas as ordens de serviço concluídas", description = "Retorna todas as ordens de serviços concluídas e não faturadas.")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<OrderDoneToInvoiceResponse>> getDoneToInvoice() {
        List<OrderDoneToInvoiceResponse> response = service.getOrdersDoneToInvoice();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/filtered/{filter}")
    @Operation(summary = "Obter todas as ordens de serviço filtradas", description = "Retorna todas as ordens de serviços com filtro.")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<OrderFilteredResponse>> getFiltered(@PathVariable String filter) {
        List<OrderFilteredResponse> response = service.getOrdersFiltered(filter);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/invoiced/{invoiceId}")
    @Operation(summary = "Obter todas as ordens de serviço de uma fatura", description = "Retorna todas as ordens de serviços de uma fatura pelo identificador único da fatura.")
    public ResponseEntity<List<OrderInvoicedResponse>> getInvoiced(@PathVariable Long invoiceId) {
        List<OrderInvoicedResponse> response = service.getOrdersInvoiced(invoiceId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/professionals/{invoiceId}")
    @Operation(summary = "Obter todos os profissional de uma fatura", description = "Retorna todas os profissional de uma fatura.")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ProfessionalNameIdResponse>> getProfessionalsByInvoiceId(@PathVariable Long invoiceId) {
        List<ProfessionalNameIdResponse> response = service.getOrderProfessionalByInvoice(invoiceId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Obter a ordem de serviço por Id", description = "Retorna a ordem de serviço pelo seu identificador único.")
    public ResponseEntity<OrderResponse> getById(@PathVariable Long id) {
        OrderResponse response = service.findById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar uma nova ordem de seviço", description = "Insere uma nova ordem de serviço na base de dados.")
    public ResponseEntity<OrderResponse> create(@Valid @RequestBody OrderCreateRequest order) {
        OrderResponse response = service.create(order);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Atualizar uma ordem de serviço", description = "Atualiza as informações de uma ordem de serviço existente.")
    public ResponseEntity<OrderResponse> update(@RequestBody OrderUpdateRequest order) {
        OrderResponse response = service.update(order);
        return ResponseEntity.ok(response);
    }

    @PutMapping("update-invoice/{invoiceId}")
    @Operation(summary = "Insere ordens de serviço à uma fatura", description = "Insere uma relações de orden de serviço à uma fatura existente.")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity updateToInvoice(@PathVariable Long invoiceId, @RequestBody List<Long> orders) {
        service.updateToInvoice(invoiceId, orders);
        return ResponseEntity.ok().build();
    }

    @PutMapping("update-status/{orderId},{status}")
    @Operation(summary = "Atualizar o status de uma ordem de serviço", description = "Atualiza o status de uma ordem de serviço existente.")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity updateStatus(@PathVariable Long orderId, @PathVariable int status) {
        service.updateStatus(orderId, status);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir uma ordem de serviço por Id", description = "Exclui uma ordem de serviço pelo seu identificador único.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}