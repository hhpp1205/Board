package team.cupid.realworld.global.common;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Getter
@NoArgsConstructor
public class CustomPageResponse<T> implements Serializable {

    private List<T> data = new ArrayList<>();

    private int totalPage;

    private long totalElement;

    private long number;

    @Builder
    public CustomPageResponse(List<T> data, int totalPage, long totalElement, long number) {
        this.data = data;
        this.totalPage = totalPage;
        this.totalElement = totalElement;
        this.number = number;
    }

    public static <T> CustomPageResponse<T> of(Page<T> page) {
        return CustomPageResponse.<T>builder()
                .data(page.getContent())
                .totalPage(page.getTotalPages())
                .totalElement(page.getTotalElements())
                .number(page.getNumber())
                .build();
    }

    public static <T> CustomPageResponse<T> of(Page<T> page, List<T> data) {
        return CustomPageResponse.<T>builder()
                .data(data)
                .totalPage(page.getTotalPages())
                .totalElement(page.getTotalElements())
                .number(page.getNumber())
                .build();
    }



}
