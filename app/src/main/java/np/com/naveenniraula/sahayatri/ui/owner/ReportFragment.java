package np.com.naveenniraula.sahayatri.ui.owner;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import np.com.naveenniraula.sahayatri.R;

public class ReportFragment extends BaseFragment {

    private ReportViewModel mViewModel;

    public static ReportFragment newInstance() {
        return new ReportFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.report_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        changeTitle(R.string.title_graph);

        setupGraph();
    }

    private void setupGraph() {
        GraphView graph = getView().findViewById(R.id.graph);
        DataPoint[] dataPoints = new DataPoint[100];

        for (int i = 0; i < dataPoints.length; i++) {

            dataPoints[i] = new DataPoint(i, i + 10);
        }
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints);
        graph.getViewport().setScalable(true);
        graph.addSeries(series);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ReportViewModel.class);
    }

}
