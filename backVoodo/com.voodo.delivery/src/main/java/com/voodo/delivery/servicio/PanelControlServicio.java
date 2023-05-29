package com.voodo.delivery.servicio;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface PanelControlServicio {
    ResponseEntity<Map<String,Object>>getCount();
}
