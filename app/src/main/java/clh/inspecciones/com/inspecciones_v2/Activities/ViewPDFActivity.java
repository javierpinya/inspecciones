package clh.inspecciones.com.inspecciones_v2.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

import clh.inspecciones.com.inspecciones_v2.R;

public class ViewPDFActivity extends AppCompatActivity {
    private PDFView pdfView;
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pdf);

        pdfView = findViewById(R.id.pdfView);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            file = new File(bundle.getString("path", "error"));
        }

        pdfView.fromFile(file)
                .enableSwipe(true)
                .swipeHorizontal(true)
                .enableDoubletap(true)
                .enableAntialiasing(true)
                .load();
    }
}
