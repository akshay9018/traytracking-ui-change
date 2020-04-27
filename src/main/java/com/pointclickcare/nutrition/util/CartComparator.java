package com.pointclickcare.nutrition.util;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Comparator;
import java.util.Map.Entry;

import org.springframework.stereotype.Component;

import com.pointclickcare.nutrition.bean.UICart;

@Component
public class CartComparator implements Comparator<Entry<Long, UICart>>, Serializable
{

  @Override
  public int compare(Entry<Long, UICart> o1, Entry<Long, UICart> o2)
  {
    return LocalTime.parse(o1.getValue().getMinimumDeliveryTime(), DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))
        .compareTo(LocalTime.parse(o2.getValue().getMinimumDeliveryTime(), DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)));
  }
}
