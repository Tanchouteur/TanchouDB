package fr.tanchou.language;

import fr.tanchou.enums.InstructionType;
import fr.tanchou.enums.StructureType;

import java.util.List;

public class Operation {
    private final InstructionType instructionType;
    private final StructureType structureType;
    private final List<String> parameters;

    public Operation(InstructionType instructionType, StructureType structureType, List<String> parameters) {
        this.instructionType = instructionType;
        this.structureType = structureType;
        this.parameters = parameters;
    }

    public InstructionType getInstructionType() {
        return instructionType;
    }

    public StructureType getStructureType() {
        return structureType;
    }

    public List<String> getParameters() {
        return parameters;
    }
}
