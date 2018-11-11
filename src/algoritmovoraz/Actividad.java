package algoritmovoraz;

public class Actividad {
	private int id;
	private int inicio;
	private int fin;
	
	public Actividad(int id, int inicio, int fin){
		this.id = id;
		this.inicio = inicio;
		this.fin = fin;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getInicio() {
		return inicio;
	}

	public void setInicio(int inicio) {
		this.inicio = inicio;
	}

	public int getFin() {
		return fin;
	}

	public void setFin(int fin) {
		this.fin = fin;
	}

	@Override
	public String toString() {
		return "Actividad [id=" + id + ", inicio=" + inicio + ", fin=" + fin + "]";
	}
}
