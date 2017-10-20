package ru.zaochno.zaochno.data.event;

import ru.zaochno.zaochno.data.model.SubChapter;

public class SubChapterSelectedEvent {
    private SubChapter subChapter;

    public SubChapterSelectedEvent(SubChapter subChapter) {
        this.subChapter = subChapter;
    }

    public SubChapter getSubChapter() {
        return subChapter;
    }
}
