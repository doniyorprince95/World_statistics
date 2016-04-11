package com.ikuchko.world_population.util;

/**
 * Created by iliak on 4/10/16.
 */
public interface ItemTouchHelperAdapter {
    boolean onItemMove(int fromPosition, int toPosition);
    void onItemDismiss(int position);
}
