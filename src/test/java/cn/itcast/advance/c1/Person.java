package cn.itcast.advance.c1;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author madepeng
 * @title: Person
 * @projectName netty-demo
 * @description: TODO
 * @date 2021/12/31 12:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person implements Serializable {
    private String name;
}
