package view;
import controller.*;
import model.*;
import dao.*;

import java.awt.Image;
import java.io.File;
import javax.swing.ImageIcon;

// utilitario pra carregar icones dos assets de forma robusta,
// tentando varios caminhos possiveis
public class IconUtils {

    private static final String[] BASE_PATHS = {
        "assets/Icones/",
        "../assets/Icones/",
        "../../assets/Icones/",
        System.getProperty("user.dir") + "/assets/Icones/",
        System.getProperty("user.dir") + "/../assets/Icones/"
    };

    // tenta carregar o icone nas pastas conhecidas
    public static ImageIcon loadIcon(String filename) {
        for (String base : BASE_PATHS) {
            File f = new File(base + filename);
            if (f.exists()) {
                return new ImageIcon(f.getAbsolutePath());
            }
        }
        // tenta caminho absoluto caso o usuario passe
        File f = new File(filename);
        if (f.exists()) {
            return new ImageIcon(f.getAbsolutePath());
        }
        return null;
    }

    // carrega e redimensiona mantendo proporcao
    public static ImageIcon loadIcon(String filename, int width, int height) {
        ImageIcon icon = loadIcon(filename);
        if (icon != null) {
            Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        }
        return null;
    }
}
