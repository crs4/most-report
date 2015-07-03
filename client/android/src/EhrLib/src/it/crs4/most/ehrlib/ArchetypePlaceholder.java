package it.crs4.most.ehrlib;

public class ArchetypePlaceholder {

	private String archetypeClass;
	private int containerId;
	
	public String getArchetypeClass() {
		return archetypeClass;
	}

	public int getContainerId() {
		return containerId;
	}

	public ArchetypePlaceholder(String archetypeClass, int containerId)
	{
		this.archetypeClass = archetypeClass;
		this.containerId = containerId;
	}
}
