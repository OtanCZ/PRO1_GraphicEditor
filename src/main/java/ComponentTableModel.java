import javax.swing.table.AbstractTableModel;

public class ComponentTableModel extends AbstractTableModel {

    private final ComponentList components;

    private final String[] columnNames = {"Name", "Width", "Height"};

    public ComponentTableModel(ComponentList components) {
        this.components = components;
    }

    @Override
    public int getRowCount() {
        return components.getComponents().size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex){
            case 0: {
                return String.class;
            }
            case 1 : {
                return Integer.class;
            }
        }
        return Object.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 0;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0: {
                return components.getComponents().get(rowIndex).getName();
            }
            case 1: {
                return components.getComponents().get(rowIndex).getWidth();
            }
        }
        return "";
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if(columnIndex == 0) {
            components.getComponents().get(rowIndex).setName((String) aValue);
        }
    }
}
