package es.unavarra.tlm.dscr_25_06;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.VH> {

    public interface OnCellClick {
        void onCellClick(BoardCell cell, int position);
    }

    private final Context ctx;
    private final List<BoardCell> items;
    private final OnCellClick cb;
    private int cellSize = 0;

    // Colores del tema
    private final int COLOR_WATER;
    private final int COLOR_SHIP;
    private final int COLOR_SELECTED;
    private final int COLOR_HIT;
    private final int COLOR_MISS;
    private final int COLOR_RECEIVED_HIT;
    private final int COLOR_RECEIVED_MISS;
    private final int COLOR_BORDER;

    public BoardAdapter(Context ctx, List<BoardCell> items, OnCellClick cb) {
        this.ctx = ctx;
        this.items = items;
        this.cb = cb;
        calcularTamañoCelda();

        // Cargar colores del tema
        COLOR_WATER = ContextCompat.getColor(ctx, R.color.board_water);
        COLOR_SHIP = ContextCompat.getColor(ctx, R.color.board_ship);
        COLOR_SELECTED = ContextCompat.getColor(ctx, R.color.board_selected);
        COLOR_HIT = ContextCompat.getColor(ctx, R.color.board_hit);
        COLOR_MISS = ContextCompat.getColor(ctx, R.color.board_miss);
        COLOR_RECEIVED_HIT = ContextCompat.getColor(ctx, R.color.board_received_hit);
        COLOR_RECEIVED_MISS = ContextCompat.getColor(ctx, R.color.board_received_miss);
        COLOR_BORDER = ContextCompat.getColor(ctx, R.color.board_border);
    }

    private void calcularTamañoCelda() {
        DisplayMetrics dm = ctx.getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels;
        int padding = dp(32);
        int availableWidth = screenWidth - padding;
        cellSize = availableWidth / 10;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View cell = new View(ctx);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(cellSize, cellSize);
        cell.setLayoutParams(lp);
        return new VH(cell);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int pos) {
        BoardCell cell = items.get(pos);
        View v = h.itemView;

        int color;
        if (cell.selected) {
            color = COLOR_SELECTED;
        } else if (cell.shotDone) {
            color = cell.shotHit ? COLOR_HIT : COLOR_MISS;
        } else if (cell.shotReceived) {
            color = cell.receivedHit ? COLOR_RECEIVED_HIT : COLOR_RECEIVED_MISS;
        } else if (cell.myShip) {
            color = COLOR_SHIP;
        } else {
            color = COLOR_WATER;
        }

        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setColor(color);
        drawable.setStroke(2, COLOR_BORDER);
        drawable.setCornerRadius(4);
        v.setBackground(drawable);

        v.setOnClickListener(view -> {
            if (cb != null) cb.onCellClick(cell, pos);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        public VH(@NonNull View itemView) {
            super(itemView);
        }
    }

    private int dp(int v) {
        return Math.round(v * ctx.getResources().getDisplayMetrics().density);
    }
}