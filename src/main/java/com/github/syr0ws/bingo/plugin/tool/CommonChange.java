package com.github.syr0ws.bingo.plugin.tool;

import com.github.syr0ws.bingo.api.tool.Change;
import com.github.syr0ws.bingo.api.tool.ChangeData;
import com.github.syr0ws.bingo.api.tool.ChangeType;

public class CommonChange implements Change {

    private final ChangeType type;
    private final ChangeData data;

    public CommonChange(ChangeType type, ChangeData data) {

        if(type == null)
            throw new IllegalArgumentException("ChangeType cannot be null.");

        if(data == null)
            throw new IllegalArgumentException("ChangeData cannot be null.");

        this.type = type;
        this.data = data;
    }

    @Override
    public ChangeType getType() {
        return this.type;
    }

    @Override
    public ChangeData getData() {
        return this.data;
    }
}
