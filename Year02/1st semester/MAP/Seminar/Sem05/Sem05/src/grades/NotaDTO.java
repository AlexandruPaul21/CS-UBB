package grades;

public class NotaDTO {
    private String studentName;
    private String profesor;
    private String temaId;
    private double nota;

    public NotaDTO(String studentName, String profesor, String temaId, double nota) {
        this.studentName = studentName;
        this.profesor = profesor;
        this.temaId = temaId;
        this.nota = nota;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getProfesor() {
        return profesor;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    public String getTemaId() {
        return temaId;
    }

    public void setTemaId(String temaId) {
        this.temaId = temaId;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    @Override
    public String toString() {
        return "NotaDTO{" +
                "studentName='" + studentName + '\'' +
                ", profesor='" + profesor + '\'' +
                ", temaId='" + temaId + '\'' +
                ", nota=" + nota +
                '}';
    }
}
