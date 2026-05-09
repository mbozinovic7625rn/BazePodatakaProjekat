package controller;

import config.Config;
import model.IstrazivacDTO;
import model.LaboratorijaDTO;

import java.util.List;

public class LaboratorijaController {

    public static List<LaboratorijaDTO> sveLaboratorije() {
        return LaboratorijaDTO.selectAll(Config.getConnection());
    }

    public static List<IstrazivacDTO> istrazivaciZaLab(int idLab) {
        return IstrazivacDTO.selectAllByLab(idLab,  Config.getConnection());
    }

    private LaboratorijaController() {}
}
