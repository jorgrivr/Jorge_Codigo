package com.voodo.delivery.restImpl;

import com.voodo.delivery.rest.PanelControlRest;
import com.voodo.delivery.servicio.PanelControlServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class PanelControlRestImpl implements PanelControlRest {
    @Autowired
    PanelControlServicio panelControlServicio;
    @Override
    public ResponseEntity<Map<String, Object>> getCount() {
        return panelControlServicio.getCount();
    }
}
