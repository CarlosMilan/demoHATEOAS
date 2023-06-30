package com.ejemplo.demo.service;

import com.ejemplo.demo.dto.ClienteDTO;
import com.ejemplo.demo.model.Cliente;
import com.ejemplo.demo.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository; //La conexion a la base de datos

    public ClienteDTO getClienteById(Long id) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);
        Cliente cliente = clienteOptional.orElse(new Cliente());

        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setApellido(cliente.getApellido());
        clienteDTO.setNombre(cliente.getNombre());
        clienteDTO.setEmail(cliente.getEmail());
        clienteDTO.setFechaNacimiento(cliente.getFechaNacimiento());

        return clienteDTO;
    }

    public Cliente saveCliente(ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente();
        cliente.setApellido(clienteDTO.getApellido());
        cliente.setNombre(clienteDTO.getNombre());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setFechaNacimiento(clienteDTO.getFechaNacimiento());

        return clienteRepository.save(cliente);
    }

    public Cliente updateCliente(ClienteDTO clienteDTO, Long id) {
        Cliente cliente = new Cliente();
        if (clienteRepository.existsById(id)) {
            cliente.setEmail(clienteDTO.getEmail());
            cliente.setId(id);
            cliente.setNombre(clienteDTO.getNombre());
            cliente.setApellido(clienteDTO.getApellido());
            cliente.setFechaNacimiento(clienteDTO.getFechaNacimiento());
            clienteRepository.save(cliente);
        }
        return cliente;
    }

    public void deleteCliente(Long id) {
        clienteRepository.deleteById(id);
    }
}
