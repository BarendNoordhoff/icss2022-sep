package nl.han.ica.datastructures;

import nl.han.ica.icss.ast.types.ExpressionType;

import java.util.HashMap;

public class VariableManager<T,A> {
    private IHANLinkedList<HashMap<T,A>> variableTypes;

    public VariableManager() {}

    public void setVariableTypes(IHANLinkedList<HashMap<T, A>> variableTypes) {
        this.variableTypes = variableTypes;
    }

    public A get(T key) {
        for (HashMap<T,A> scope : variableTypes) {
            A value = scope.get(key);
            if (value != null) {
                return value;
            }
        }
        return null;
    }

    public void add(HashMap<T,A> newScope) {
        variableTypes.addFirst(newScope);
    }

    public void delete() {
        variableTypes.removeFirst();
    }
}
