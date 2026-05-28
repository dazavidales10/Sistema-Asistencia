package Vista; // 📦 El paquete donde está la clase

import javax.swing.*; // 📚 Librerías de Swing (ventanas, botones, etiquetas)
import java.awt.*;    // 📚 Librerías de AWT (colores, fuentes, layouts)

public class PanelAprendiz extends JFrame { // 🖼️ Clase PanelAprendiz que hereda de JFrame (ventana)

    // 🎯 Constructor principal con parámetros
    public PanelAprendiz(String nombre, String ficha, int asistencia, int faltas, int tardanzas) {
        setTitle("Panel Aprendiz");              // 🏷️ Título de la ventana
        setSize(900, 600);                       // 📐 Tamaño inicial pensado para 1366x768
        setDefaultCloseOperation(EXIT_ON_CLOSE); // ❌ Cierra el programa al cerrar la ventana
        setLocationRelativeTo(null);             // 📍 Centra la ventana en la pantalla
        setLayout(new BorderLayout());           // 📐 Layout principal responsive (divide en norte, centro, sur)

        // 🟩 Encabezado verde arriba
        JLabel header = new JLabel("Panel Aprendiz", SwingConstants.CENTER); // Texto centrado
        header.setOpaque(true);                           // Permite mostrar color de fondo
        header.setBackground(new Color(0, 180, 0));       // 🎨 Fondo verde
        header.setForeground(Color.WHITE);                // 🎨 Texto blanco
        header.setFont(new Font("Arial", Font.BOLD, 28)); // ✍️ Fuente grande para título
        header.setPreferredSize(new Dimension(900, 70));  // 📐 Altura fija del encabezado
        add(header, BorderLayout.NORTH);                  // ➕ Añade el encabezado en la parte superior

        // 📋 Panel central con datos del aprendiz
        JPanel centerPanel = new JPanel(new GridBagLayout()); // 📐 Layout flexible en cuadrícula
        centerPanel.setBackground(Color.WHITE);               // 🎨 Fondo blanco
        GridBagConstraints gbc = new GridBagConstraints();    // 📐 Configuración de posiciones
        gbc.insets = new Insets(15, 15, 15, 15);              // 📐 Márgenes entre elementos
        gbc.anchor = GridBagConstraints.WEST;                 // 📍 Alineación a la izquierda
        gbc.gridx = 0; gbc.gridy = 0;                         // 📐 Primera posición

        JLabel lblBienvenido = new JLabel("Bienvenido: " + nombre); // 👤 Muestra nombre
        lblBienvenido.setFont(new Font("Arial", Font.PLAIN, 22));   // ✍️ Fuente mediana
        centerPanel.add(lblBienvenido, gbc);                        // ➕ Añade al panel

        gbc.gridy++; // 📐 Siguiente fila
        JLabel lblFicha = new JLabel("Ficha: " + ficha);            // 📄 Muestra ficha
        lblFicha.setFont(new Font("Arial", Font.PLAIN, 22));
        centerPanel.add(lblFicha, gbc);

        gbc.gridy++;
        JLabel lblAsistencia = new JLabel("Asistencia: " + asistencia + "%"); // 📊 Muestra asistencia
        lblAsistencia.setFont(new Font("Arial", Font.PLAIN, 22));
        centerPanel.add(lblAsistencia, gbc);

        gbc.gridy++;
        JLabel lblFaltas = new JLabel("Faltas: " + faltas); // ❌ Muestra faltas
        lblFaltas.setFont(new Font("Arial", Font.PLAIN, 22));
        centerPanel.add(lblFaltas, gbc);

        gbc.gridy++;
        JLabel lblTardanzas = new JLabel("Tardanzas: " + tardanzas); // ⏰ Muestra tardanzas
        lblTardanzas.setFont(new Font("Arial", Font.PLAIN, 22));
        centerPanel.add(lblTardanzas, gbc);

        add(centerPanel, BorderLayout.CENTER); // ➕ Añade el panel central

        // 🔘 Panel inferior con botones
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 20)); // 📐 Distribución adaptable
        bottomPanel.setBackground(Color.WHITE); // 🎨 Fondo blanco

        JButton btnHistorial = new JButton("Ver Historial");             // Botón historial
        JButton btnExcusa = new JButton("Enviar Excusa");                // Botón excusa
        JButton btnConsultar = new JButton("Consultar Asistencia");      // Botón asistencia
        JButton btnCerrar = new JButton("Cerrar Sesión");                // Botón cerrar

        // 🎨 Estilo uniforme para botones
        JButton[] botones = {btnHistorial, btnExcusa, btnConsultar, btnCerrar};
        for (JButton b : botones) {
            b.setBackground(new Color(0, 180, 0));       // 🎨 Fondo verde
            b.setForeground(Color.WHITE);                // 🎨 Texto blanco
            b.setFocusPainted(false);                    // 🔧 Quita borde de enfoque
            b.setFont(new Font("Arial", Font.BOLD, 18)); // ✍️ Texto más grande
            b.setPreferredSize(new Dimension(220, 55));  // 📐 Tamaño uniforme
            bottomPanel.add(b);                          // ➕ Añade al panel inferior
        }

        add(bottomPanel, BorderLayout.SOUTH); // ➕ Añade el panel inferior

        // 🛠️ Acción del botón cerrar sesión
        btnCerrar.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Sesión cerrada"); // 📢 Mensaje emergente
            dispose(); // ❌ Cierra la ventana actual
        });
    }

    // 🧪 Constructor vacío para pruebas
    public PanelAprendiz() {
        this("Prueba", "0000", 0, 0, 0); // 🔄 Llama al constructor principal con datos ficticios
    }

    // 🚀 Método main para probarlo directamente
    public static void main(String[] args) {
        new PanelAprendiz("Pedro Gomez", "3364343", 92, 3, 2).setVisible(true); // 🖥️ Abre ventana de prueba
    }
}
