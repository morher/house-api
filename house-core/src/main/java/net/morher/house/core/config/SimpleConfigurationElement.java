package net.morher.house.core.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import net.morher.house.api.config.ConfigurationElement;

/**
 * A simple implementationd of {@link ConfigurationElement} and {@link EditableConfigurationElement}.
 * 
 * @author Morten Hermansen
 */
public class SimpleConfigurationElement implements EditableConfigurationElement {
    private Map<String, SimpleConfigurationElement> subNodes;
    private List<SimpleConfigurationElement> nodeList;
    private String value;
    private SimpleConfigurationElement parent;

    public static final EditableConfigurationElement create() {
        return new SimpleConfigurationElement();
    }

    public SimpleConfigurationElement add() {
        if (parent != null && parent.isList()) {
            return parent.add();
        }
        if (!isList()) {
            convertToList();
        }
        SimpleConfigurationElement node = new SimpleConfigurationElement();
        nodeList.add(node);
        return node;
    }

    private void convertToList() {
        nodeList = new ArrayList<>();

        if (isObject() || value != null) {
            SimpleConfigurationElement firstItem = new SimpleConfigurationElement();
            firstItem.subNodes = subNodes;
            subNodes = null;

            firstItem.value = value;

            value = null;
            nodeList.add(firstItem);
        }
    }

    private boolean isObject() {
        return subNodes != null && !subNodes.isEmpty();
    }

    private boolean isList() {
        return nodeList != null;
    }

    private SimpleConfigurationElement getSubNode(String name, boolean attachSubNode) {
        if (nodeList != null && !nodeList.isEmpty()) {
            return nodeList.get(0).getSubNode(name, attachSubNode);
        }
        SimpleConfigurationElement subNode = null;
        if (subNodes == null) {
            subNodes = new HashMap<>();
        }
        subNode = subNodes.get(name);
        if (subNode == null) {
            subNode = new SimpleConfigurationElement();
            subNode.parent = this;
            if (attachSubNode) {
                subNodes.put(name, subNode);
            }
        }
        return subNode;
    }

    @Override
    public Iterator<ConfigurationElement> iterator() {
        if (nodeList != null) {
            return new ArrayList<ConfigurationElement>(nodeList).iterator();
        }
        return Collections.emptyIterator();
        // return Collections.<HouseConfiguration> singletonList(this).iterator();
    }

    @Override
    public ConfigurationElement get(String path) {
        return locate(path, false);
    }

    public EditableConfigurationElement with(String path) {
        return locate(path, true);
    }

    private SimpleConfigurationElement locate(String path, boolean attachNewSubNode) {
        String parts[] = path.split("\\.");
        SimpleConfigurationElement node = this;
        for (String part : parts) {
            node = node.getSubNode(part, attachNewSubNode);
        }
        return node;
    }

    @Override
    public Optional<String> asString() {
        if (nodeList != null && !nodeList.isEmpty()) {
            nodeList.get(0).asString();
        }
        if (value == null || value.isBlank()) {
            return Optional.empty();
        }
        return Optional.of(value);
    }

    @Override
    public Optional<Integer> asInteger() {
        return asString()
                .map(Integer::valueOf);
    }

    @Override
    public Optional<Boolean> asBoolean() {
        return asString()
                .map(Boolean::valueOf);
    }

    private void setValue(String value) {
        this.value = value;
    }

    @Override
    public EditableConfigurationElement set(String value) {
        setValue(value);
        return this;
    }

    @Override
    public EditableConfigurationElement set(int value) {
        setValue(Integer.toString(value));
        return this;
    }

    @Override
    public EditableConfigurationElement set(boolean value) {
        setValue(Boolean.toString(value));
        return this;
    }
}