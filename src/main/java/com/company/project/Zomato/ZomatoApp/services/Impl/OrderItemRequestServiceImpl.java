package com.company.project.Zomato.ZomatoApp.services.Impl;

import com.company.project.Zomato.ZomatoApp.entities.OrderItemRequest;
import com.company.project.Zomato.ZomatoApp.exceptions.ResourceNotFoundException;
import com.company.project.Zomato.ZomatoApp.repositories.OrderItemRequestRepository;
import com.company.project.Zomato.ZomatoApp.services.OrderItemRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class OrderItemRequestServiceImpl implements OrderItemRequestService {

    private final OrderItemRequestRepository orderItemRequestRepository;


    @Override
    public OrderItemRequest findOrderItemRequestById(Long orderItemRequestId) {
       return orderItemRequestRepository.findById(orderItemRequestId)
               .orElseThrow(()-> new ResourceNotFoundException("OrderItemRequest not found with id: "+orderItemRequestId));
    }

    @Override
    public void update(OrderItemRequest orderItemRequest) {
      OrderItemRequest toSave = orderItemRequestRepository.findById(orderItemRequest.getId())
              .orElseThrow(()-> new ResourceNotFoundException(("OrderItemRequest not found with id"+ orderItemRequest.getId())));

      orderItemRequestRepository.save(orderItemRequest);
    }
}
