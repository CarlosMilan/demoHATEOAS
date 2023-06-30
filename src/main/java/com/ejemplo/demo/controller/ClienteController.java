package com.ejemplo.demo.controller;

import com.ejemplo.demo.dto.ClienteDTO;
import com.ejemplo.demo.model.Cliente;
import com.ejemplo.demo.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Cliente> getCliente(@PathVariable Long id){
        Cliente cliente = clienteService.getClienteById(id);

        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setEmail(cliente.getEmail());
        clienteDTO.setApellido(cliente.getApellido());
        clienteDTO.setNombre(cliente.getNombre());

        clienteDTO.setFechaNacimiento(cliente.getFechaNacimiento());
        cliente.add(linkTo(methodOn(ClienteController.class).getCliente(cliente.getId())).withSelfRel(),
                linkTo(methodOn(ClienteController.class).saveCliente(clienteDTO)).withRel("Guardar Cliente"),
                linkTo(methodOn(ClienteController.class).updateCliente(clienteDTO, cliente.getId())).withRel("Actualizar Cliente"),
                linkTo(methodOn(ClienteController.class).deleteCliente(cliente.getId())).withRel("Eliminar Cliente"));
        return ResponseEntity.ok().body(cliente);
    }

    @PostMapping()
    public ResponseEntity<Cliente> saveCliente(@RequestBody ClienteDTO clienteDTO) {

        Cliente clienteGuardado = clienteService.saveCliente(clienteDTO);

        return new ResponseEntity<>(clienteGuardado, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> updateCliente(@RequestBody ClienteDTO clienteDTO, @PathVariable Long id) {
        return new ResponseEntity<>(clienteService.updateCliente(clienteDTO, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        clienteService.deleteCliente(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
