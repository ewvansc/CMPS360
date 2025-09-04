import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class assign2 extends JFrame {
    // --- Inputs ---
    private final JComboBox<String> typeCombo = new JComboBox<>(new String[]{"Movie", "Series"});

    private final JTextField titleField       = new JTextField(20);
    private final JTextField genreField       = new JTextField(20);
    private final JTextField yearField        = new JTextField(8);
    private final JTextField directorField    = new JTextField(20);
    private final JTextField mainActorField   = new JTextField(20);
    private final JTextField mainActressField = new JTextField(20);
    private final JTextField languageField    = new JTextField(15);
    private final JTextField durationField    = new JTextField(6);
    private final JTextField ratingField      = new JTextField(5);
    private final JTextField ageField         = new JTextField(5);
    private final JTextField seasonsField     = new JTextField(5);
    private final JTextField episodesField    = new JTextField(5);

    private final JPanel seriesPanel = new JPanel(new GridLayout(0, 2, 6, 6));
    private final JTextArea outputArea = new JTextArea(12, 48);

    public assign2() {
        super("Netflix Movie Management");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        
        JPanel form = new JPanel(new GridLayout(0, 2, 6, 6));
        form.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

       
        addRow(form, "Content Type", typeCombo);
        addRow(form, "Title", titleField);
        addRow(form, "Genre", genreField);
        addRow(form, "Release Year", yearField);
        addRow(form, "Director", directorField);
        addRow(form, "Main Actor", mainActorField);
        addRow(form, "Main Actress", mainActressField);
        addRow(form, "Language", languageField);
        addRow(form, "Duration (min)", durationField);
        addRow(form, "IMDb Rating", ratingField);
        addRow(form, "Viewer Age Restriction", ageField);

        
        addRow(seriesPanel, "Number of Seasons", seasonsField);
        addRow(seriesPanel, "Number of Episodes", episodesField);

        
        JPanel center = new JPanel(new BorderLayout(0, 10));
        center.add(form, BorderLayout.NORTH);
        center.add(seriesPanel, BorderLayout.CENTER);
        add(center, BorderLayout.CENTER);

        
        typeCombo.addActionListener(e -> toggleSeries());

        
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 6));
        JButton submitBtn = new JButton("Submit");
        JButton clearBtn  = new JButton("Clear");
        buttons.add(submitBtn);
        buttons.add(clearBtn);

        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        outputArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        JScrollPane scroll = new JScrollPane(outputArea);
        scroll.setBorder(BorderFactory.createTitledBorder("Output"));

        JPanel south = new JPanel(new BorderLayout(10, 6));
        south.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        south.add(buttons, BorderLayout.NORTH);
        south.add(scroll, BorderLayout.CENTER);
        add(south, BorderLayout.SOUTH);

        
        submitBtn.addActionListener(this::handleSubmit);
        clearBtn.addActionListener(e -> clearAll());

        toggleSeries();  
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addRow(JPanel parent, String label, JComponent field) {
        parent.add(new JLabel(label + ":"));
        parent.add(field);
    }

    private void toggleSeries() {
        boolean isSeries = "Series".equals(typeCombo.getSelectedItem());
        seriesPanel.setVisible(isSeries);
        seasonsField.setEnabled(isSeries);
        episodesField.setEnabled(isSeries);
        revalidate();
        repaint();
    }

    private void handleSubmit(ActionEvent e) {
        
        String type = (String) typeCombo.getSelectedItem();
        String title = titleField.getText().trim();
        String genre = genreField.getText().trim();

        if (title.isEmpty()) { error("Movie Title cannot be empty."); return; }
        if (genre.isEmpty()) { error("Genre cannot be empty."); return; }

        Integer year = parseInt(yearField.getText(), "Release Year must be numeric.");
        if (year == null) return;

        Integer duration = parsePositiveInt(durationField.getText(), "Duration must be a positive number (minutes).");
        if (duration == null) return;

        Double rating = parseDouble(ratingField.getText(), "IMDb Rating must be a number between 0.0 and 10.0.");
        if (rating == null || rating < 0.0 || rating > 10.0) {
            error("IMDb Rating must be between 0.0 and 10.0.");
            return;
        }

        Integer age = parsePositiveInt(ageField.getText(), "Viewer Age Restriction must be a positive number.");
        if (age == null) return;

        Integer seasons = null, episodes = null;
        if ("Series".equals(type)) {
            seasons = parsePositiveInt(seasonsField.getText(), "Number of Seasons must be a positive number.");
            if (seasons == null) return;
            episodes = parsePositiveInt(episodesField.getText(), "Number of Episodes must be a positive number.");
            if (episodes == null) return;
        }

        
        StringBuilder sb = new StringBuilder();
        sb.append("Movie Details:\n\n");
        sb.append(formatKV("Title", title)).append('\n');
        sb.append(formatKV("Genre", genre)).append('\n');
        sb.append(formatKV("Release Year", String.valueOf(year))).append('\n');
        sb.append(formatKV("Director", directorField.getText().trim())).append('\n');
        sb.append(formatKV("Main Actor", mainActorField.getText().trim())).append('\n');
        sb.append(formatKV("Main Actress", mainActressField.getText().trim())).append('\n');
        sb.append(formatKV("Language", languageField.getText().trim())).append('\n');
        sb.append(formatKV("Duration", duration + " minutes")).append('\n');
        sb.append(formatKV("IMDb Rating", String.valueOf(rating))).append('\n');
        sb.append(formatKV("Viewer Restriction", age + "+"));

        if ("Series".equals(type)) {
            sb.append('\n').append(formatKV("Number of Seasons", String.valueOf(seasons)));
            sb.append('\n').append(formatKV("Number of Episodes", String.valueOf(episodes)));
        }

        outputArea.setText(sb.toString());
    }

    
    private String formatKV(String key, String value) {
        return String.format("%-17s: %s", key, value);
    }

    private void clearAll() {
        JTextField[] fields = {
            titleField, genreField, yearField, directorField, mainActorField,
            mainActressField, languageField, durationField, ratingField, ageField,
            seasonsField, episodesField
        };
        for (JTextField f : fields) f.setText("");
        typeCombo.setSelectedIndex(0);
        outputArea.setText("");
        titleField.requestFocus();
    }

    private void error(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Input Error", JOptionPane.ERROR_MESSAGE);
    }

    private Integer parseInt(String s, String err) {
        try { return Integer.valueOf(s.trim()); }
        catch (Exception ex) { error(err); return null; }
    }

    private Integer parsePositiveInt(String s, String err) {
        Integer v = parseInt(s, err);
        if (v == null) return null;
        if (v <= 0) { error(err); return null; }
        return v;
    }

    private Double parseDouble(String s, String err) {
        try { return Double.valueOf(s.trim()); }
        catch (Exception ex) { error(err); return null; }
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}
        SwingUtilities.invokeLater(assign2::new);
    }
}
