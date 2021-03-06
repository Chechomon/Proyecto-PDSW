/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.BoomECI.persistencia;

import com.BoomECI.entidades.SolicitudCancelacion;
import java.util.List;

/**
 *
 * @author BoomECI
 */
public interface DirectivoDAO {   

    public List<SolicitudCancelacion> loadSolicitudesNoFinalizadas(int carrera);

    public List<SolicitudCancelacion> loadSolicitudesFinalizadas(int carrera);

    public void saveState(int idSolicitud);
}
