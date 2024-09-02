package VIEW;

import GAMESTATEMANAGER.MenuState;
import MODEL.UserProfile;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class LeaderboardPanel extends JPanel {

    // N. | AVATAR| NAME | LEVEL | HIGHSCORE | W | L | TOT
    // 5 PLAYERS MAX
    private Font font = MainFrame.getPixelFont();
    private JTable leaderboardTable;
    private JLabel menuLabel;
    private JLabel exitLabel;
    private final JLabel cursor = new JLabel();
    private int selectedOption = 0;

    public LeaderboardPanel() {
        this.setSize(MainFrame.FRAME_WIDTH, MainFrame.FRAME_HEIGHT);
        this.setBackground(Color.BLACK);
        this.setLayout(new BorderLayout());

        // Creazione del titolo in stile arcade
        JLabel titleLabel = new JLabel("LEADERBOARD", JLabel.CENTER);
        titleLabel.setFont(font.deriveFont(Font.BOLD, 40f));
        titleLabel.setForeground(Color.green);
        titleLabel.setBorder(BorderFactory.createLineBorder(Color.green, 5));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(Color.BLACK);

        // Colonne della tabella
        String[] columnNames = {"N.", "IMG", "NAME", "LV", "SCORE", "WIN", "LOSE", "TOT"};

        // Modello della tabella
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 1) { // Colonna Avatar
                    return ImageIcon.class;
                }
                return String.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Le celle non sono modificabili
            }
        };

        leaderboardTable = new JTable(tableModel);
        leaderboardTable.setFont(font.deriveFont(8f));
        leaderboardTable.setForeground(Color.GREEN); // Testo verde
        leaderboardTable.setBackground(Color.BLACK); // Sfondo della tabella nero
        leaderboardTable.setRowHeight(64); // Altezza delle righe per accomodare le immagini

        // Personalizzazione del renderer per le celle
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setBackground(Color.BLACK); // Sfondo delle celle nero
        cellRenderer.setForeground(Color.GREEN); // Testo delle celle verde
        cellRenderer.setHorizontalAlignment(JLabel.CENTER);

        // Imposta il renderer personalizzato per tutte le colonne
        for (int i = 0; i < leaderboardTable.getColumnCount(); i++) {
            leaderboardTable.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }

        // Personalizzazione dell'intestazione della tabella
        JTableHeader header = leaderboardTable.getTableHeader();
        header.setBackground(Color.DARK_GRAY); // Sfondo intestazione
        header.setForeground(Color.CYAN); // Testo intestazione
        header.setFont(font.deriveFont(Font.BOLD, 15f)); // Font intestazione
        header.setBorder(BorderFactory.createLineBorder(Color.green, 3));

        // Cambia i colori della selezione
        leaderboardTable.setSelectionBackground(Color.GREEN); // Sfondo selezione
        leaderboardTable.setSelectionForeground(Color.BLACK); // Testo selezione

        // Personalizzazione del bordo della tabella
        leaderboardTable.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));

        // Scroll pane per la tabella
        JScrollPane scrollPane = new JScrollPane(leaderboardTable);
        scrollPane.setPreferredSize(new Dimension(MainFrame.FRAME_WIDTH - 100, MainFrame.FRAME_HEIGHT - 150));
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.GREEN, 5));

        // Imposta il colore di sfondo  del JScrollPane
        scrollPane.getViewport().setBackground(Color.BLACK);
        scrollPane.setBackground(Color.BLACK);

        // titolo e la tabella in un pannello centrale
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.BLACK);
        centerPanel.add(titleLabel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        this.add(centerPanel, BorderLayout.CENTER);

        // Pannello per le etichette "MENU" e "EXIT"
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 80, 10)); // Spazio orizzontale e verticale
        bottomPanel.setBackground(Color.BLACK);

        menuLabel = new JLabel("MENU");
        menuLabel.setFont(font.deriveFont(Font.BOLD, 20f));
        menuLabel.setForeground(Color.green);
        menuLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Margini interni
        menuLabel.setVisible(true);
        menuLabel.setBackground(Color.BLACK);

        exitLabel = new JLabel("EXIT");
        exitLabel.setFont(font.deriveFont(Font.BOLD, 20f));
        exitLabel.setForeground(Color.green);
        exitLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Margini interni
        exitLabel.setVisible(true);
        exitLabel.setBackground(Color.BLACK);

        bottomPanel.add(menuLabel);
        bottomPanel.add(exitLabel);

        // Aggiungi il pannello inferiore alla parte inferiore del pannello principale
        this.add(bottomPanel, BorderLayout.SOUTH);

        this.setVisible(true);
    }

    public JLabel getMenuLabel() {
        return menuLabel;
    }

    public JLabel getExitLabel() {
        return exitLabel;
    }

    // Metodo per aggiornare i dati della leaderboard
    public void updateLeaderboard(Object[][] data) {
        DefaultTableModel model = (DefaultTableModel) leaderboardTable.getModel();
        model.setRowCount(0); // Pulisce la tabella esistente
        for (Object[] rowData : data) {
            model.addRow(rowData);
        }
    }

}


