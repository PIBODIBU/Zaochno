package ru.zaochno.zaochno.data.event;

import ru.zaochno.zaochno.data.model.Chapter;

public class ChapterSelectedEvent {
    private Chapter chapter;

    public ChapterSelectedEvent(Chapter chapter) {
        this.chapter = chapter;
    }

    public Chapter getChapter() {
        return chapter;
    }
}
