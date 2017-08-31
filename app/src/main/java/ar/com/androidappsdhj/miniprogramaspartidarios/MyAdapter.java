package ar.com.androidappsdhj.miniprogramaspartidarios;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by DarioHJ on 31/08/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<Programa> programas;
    private int layout;
    private OnItemClickListener itemClickListener;
    private Context context;


    public MyAdapter(List<Programa> programas, int layout,OnItemClickListener itemClickListener) {
        this.programas = programas;
        this.layout = layout;
        this.itemClickListener = itemClickListener;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        context = parent.getContext();
        ViewHolder vh = new ViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
        holder.bind(programas.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {
        return programas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public TextView textViewEmisora;
        public TextView textViewHora;
        public ImageView imageViewPoster;

        public ViewHolder(View v) {
            super(v);
            textViewName = (TextView) v.findViewById(R.id.txtNombrePrograma);
            textViewEmisora = (TextView)v.findViewById(R.id.txtEmisoraPrograma);
            textViewHora = (TextView)v.findViewById(R.id.txtHoraInicio);
            imageViewPoster = (ImageView) v.findViewById(R.id.imagenPrograma);
        }

        public void bind(final Programa programa, final OnItemClickListener itemClickListener) {
            //Procesamos los datos a renderizar
            textViewName.setText(programa.getNombre());
            textViewEmisora.setText(programa.getEmisora());
            if (programa.getId()== 24){
                textViewHora.setText("");
            }else{
                textViewHora.setText(programa.getDiaUno().substring(programa.getDiaUno().length()-4,programa.getDiaUno().length()) + " - ");
            }

            //Picasso
            Picasso.with(context).load(programa.getImagen()).fit().into(imageViewPoster);
            //imageViewPoster.setImageResource(movie.getPoster());

            //Definimos que por cada elemento de nuestro RecyclerView, tenemos un clickListener
            //Que se comporta de la siguiente manera
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(programa, getAdapterPosition());
                }
            });
        }
    }
    public interface OnItemClickListener {
        void onItemClick(Programa programa, int position);
    }
}
