package com.alfonso.capstone.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.alfonso.capstone.R;
import com.alfonso.capstone.contentProvider.RouteContentProvider;

import timber.log.Timber;

public class ListWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListWidgetServiceFactory(this.getApplicationContext());
    }

}

class ListWidgetServiceFactory implements RemoteViewsService.RemoteViewsFactory{
    private final Context context;
    private Cursor cursor;

    public ListWidgetServiceFactory(Context context) {
        Timber.i("Construct");
        this.context = context;
    }

    private void updateCursor() {
        Timber.d("Updating cursor...");
        Uri ROUTES_URI = RouteContentProvider.BASE_CONTENT_URI.buildUpon().appendPath(RouteContentProvider.ROUTE_TABLE).build();
        if(cursor != null) cursor.close();
        cursor = context.getContentResolver().query(
                ROUTES_URI,null,null,null,null);
    }

    @Override
    public void onCreate() {
        updateCursor();
    }

    @Override
    public void onDataSetChanged() {
        updateCursor();
    }

    @Override
    public void onDestroy() {
        cursor.close();
    }

    @Override
    public int getCount() {
        return cursor == null ? 0 : cursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        if(cursor == null || cursor.getCount() == 0) return null;
        cursor.moveToPosition(i);
        int nameId = cursor.getColumnIndex("name");
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_item);
        rv.setTextViewText(R.id.widget_item,cursor.getString(nameId));
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}