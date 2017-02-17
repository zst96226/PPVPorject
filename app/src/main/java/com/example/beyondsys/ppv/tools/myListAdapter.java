package com.example.beyondsys.ppv.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.beyondsys.ppv.R;
import com.example.beyondsys.ppv.bussiness.ImgBusiness;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by s on 2017/2/16.
 */
public class myListAdapter extends SimpleAdapter {


    String path = Environment.getExternalStorageDirectory()
            + "/listviewImg/";// 文件目录

    LayoutInflater layoutInflater;
    Context context;
    File fileDir;

//    public myListAdapter(Context context) {
//        this.context = context;
//        layoutInflater = (LayoutInflater) context
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//
//        /**
//         * 文件目录如果不存在，则创建
//         */
//        fileDir = new File(path);
//        if (!fileDir.exists()) {
//            fileDir.mkdirs();
//        }
//
//    }

    /**
     * Constructor
     *
     * @param context  The context where the View associated with this SimpleAdapter is running
     * @param data     A List of Maps. Each entry in the List corresponds to one row in the list. The
     *                 Maps contain the data for each row, and should include all the entries specified in
     *                 "from"
     * @param resource Resource identifier of a view layout that defines the views for this list
     *                 item. The layout file should include at least those named views defined in "to"
     * @param from     A list of column names that will be added to the Map associated with each
     *                 item.
     * @param to       The views that should display column in the "from" parameter. These should all be
     *                 TextViews. The first N views in this list are given the values of the first N columns
     */
    public myListAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        /**
                  * 文件目录如果不存在，则创建
                  */
        fileDir = new File(path);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }



    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.valueliststyle, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.person_img);
        TextView idtex=(TextView)view.findViewById(R.id.personid_tex);
        String id=idtex.getText().toString().trim();
        String picurl="http://120.26.37.247:8181/File/"+id+".png";
        String      name=id+".png";
        /**
         * 创建图片文件
         */
        File file = new File(fileDir,name);
        if (!file.exists()) {// 如果本地图片不存在则从网上下载
            ImgBusiness imgBusiness=new ImgBusiness();
            imgBusiness.downloadImg(picurl, name);
           // downloadPic(picNames[position], picUrls[position]);
        } else {// 图片存在则填充到listview上
            Bitmap bitmap = BitmapFactory
                    .decodeFile(file.getAbsolutePath());
            imageView.setImageBitmap(bitmap);
        }
        return view;
    }
}
