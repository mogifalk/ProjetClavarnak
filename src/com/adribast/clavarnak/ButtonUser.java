package com.adribast.clavarnak;

import java.awt.*;

public class ButtonUser extends Button {

public ButtonUser (String str) {
    super(str);
    this.setAlignmentX(LEFT_ALIGNMENT);
    this.setMinimumSize(new Dimension(Integer.MAX_VALUE,40));
}

}
