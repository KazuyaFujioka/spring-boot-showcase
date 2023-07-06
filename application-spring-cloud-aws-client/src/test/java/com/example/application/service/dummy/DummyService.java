package com.example.application.service.dummy;

import com.example.application.repository.dummy.DummyRepository;
import org.springframework.stereotype.Service;

@Service
public class DummyService {

  DummyRepository dummyRepository;

  DummyService(DummyRepository dummyRepository) {
    this.dummyRepository = dummyRepository;
  }
}
