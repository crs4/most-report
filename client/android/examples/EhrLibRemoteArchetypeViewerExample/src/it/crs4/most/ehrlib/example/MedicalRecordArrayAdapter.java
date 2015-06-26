package it.crs4.most.ehrlib.example;

import it.crs4.ehrlib.example.R;
import it.crs4.most.ehrlib.example.models.MedicalRecord;


import java.util.List;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MedicalRecordArrayAdapter extends ArrayAdapter<MedicalRecord> {

    public MedicalRecordArrayAdapter(Context context, int textViewResourceId,
                 List<MedicalRecord> objects) {
        super(context, textViewResourceId, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getViewOptimize(position, convertView, parent);
    }

    public View getViewOptimize(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                      .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.medical_record_row, null);
            viewHolder = new ViewHolder();
            viewHolder.recordId = (TextView)convertView.findViewById(R.id.textMedicalRecordId);
         
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        MedicalRecord mr = getItem(position);
        viewHolder.recordId.setText(mr.getRecordId());
        return convertView;
    }

    private class ViewHolder {
        public TextView recordId;
    }
}