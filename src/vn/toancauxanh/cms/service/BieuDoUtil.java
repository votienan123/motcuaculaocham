package vn.toancauxanh.cms.service;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.ngi.zhighcharts.SimpleExtXYModel;
import org.ngi.zhighcharts.ZHighCharts;
import org.zkoss.zul.Div;

public class BieuDoUtil {

	public static void creatColumnChart(Div divCol, List<String> chartCtg, List<Map<String, Integer>> chartData,
			String title) {
		ZHighCharts chartCol = new ZHighCharts();
		BieuDoUtil.xemBieuDoHinhCot_Column(chartCol, chartCtg, chartData, title);
		chartCol.setStyle("width:1024px; text-align:center");
		// divCol.setAlign("center");
		divCol.appendChild(chartCol);
	}

	// ================================================================================
	// Basic column: type column
	// ================================================================================
	public static void xemBieuDoHinhCot_Column(final ZHighCharts chartCompCol, final List<String> categoriesData,
			final List<Map<String, Integer>> chartData1, String title) {
		final SimpleExtXYModel dataChartModel = new SimpleExtXYModel();
		chartCompCol.setType("bar");
		chartCompCol.setTitle(title);
		final StringBuffer textCategories = new StringBuffer(400);
		for (int i = 0; i < categoriesData.size(); i++) {
			textCategories.append("'" + categoriesData.get(i) + "',");
		}
		chartCompCol.setxAxisOptions("{" + "categories: [" + textCategories + "]" + "}");
		chartCompCol.setyAxisOptions("{ " + "min:0," + "labels: {" + "overflow: 'justify'" + "}" + "}");
		chartCompCol.setTooltipFormatter("function formatTooltip(obj){ " + "return '<b>'+ obj.x +'</b><br/>"
				+ "'+obj.series.name +':' +obj.y +' người';" + "}");
		chartCompCol.setPlotOptions("{" + "bar: {" + "dataLabels: {" + "enabled: true" + "}" + "}" + "}");

		/*
		 * chartCompCol.setPlotOptions("{" + "column: {" + "dataLabels: { " +
		 * "enabled: true" + "}" + "}" + "}");
		 */

		chartCompCol.setModel(dataChartModel);

		int i = 0;
		for (final Map<String, Integer> map : chartData1) {
			for (final Entry<String, Integer> entry : map.entrySet()) {
				dataChartModel.addValue(entry.getKey(), i, entry.getValue());
				entry.getKey();
			}
			i++;
		}
	}
}
