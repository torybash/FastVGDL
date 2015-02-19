package fastVGDL.controllers.puzzleSolverPlus;

import fastVGDL.tools.Vector2i;

public class Moveable {
	public Vector2i pos = new Vector2i();
	public int type = -1;
	public int groupId = -1;
	
	public Moveable(Vector2i pos, int type, int groupId){
		this.pos = pos;
		this.type = type;
		this.groupId = groupId;
	}
	
	@Override
	public String toString() {
		return "Moveable: " + type + ", pos: " + pos + ", groupId: " + groupId;
	}
	

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pos == null) ? 0 : pos.hashCode());
		result = prime * result + type;
		result = prime * result + groupId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Moveable other = (Moveable) obj;
		if (pos == null) {
			if (other.pos != null)
				return false;
		} else if (!pos.equals(other.pos))
			return false;
		if (type != other.type)
			return false;
		if (groupId != other.groupId)
			return false;
		return true;
	}

	public boolean equals(Moveable m) {
		if (type == m.type && groupId == m.groupId && pos.equals(m.pos)) return true;
		return false;
	}
}
