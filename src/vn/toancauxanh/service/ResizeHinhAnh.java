package vn.toancauxanh.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.imgscalr.Scalr;

import com.mysema.commons.lang.Pair;

import vn.toancauxanh.gg.model.Image;
import vn.toancauxanh.model.Setting;

public class ResizeHinhAnh {
	private static transient final Logger LOG = LogManager.getLogger(ResizeHinhAnh.class.getName());

	// ---------------------
	public static void saveMediumAndSmall(Image image, String strFolderStore) throws IOException {
		LOG.info("saveMediumAndSmall image forder: " + strFolderStore);
		File file = new File(strFolderStore + image.getName());
		if (file.exists()) {
			LOG.info("File: " + image.getName());
			BufferedImage originalImage = ImageIO.read(file);

			List<Pair<Integer, Integer>> list_size = getHeightSmallAndMedium(originalImage);
			String extension = image.getName().substring(image.getName().lastIndexOf(".") + 1);

			if (list_size.size() > 0) {
				BufferedImage resizeImageHintJpg = Scalr.resize(originalImage, Scalr.Mode.FIT_EXACT,
						list_size.get(0).getFirst(), list_size.get(0).getSecond(), Scalr.OP_ANTIALIAS);

				ImageIO.write(resizeImageHintJpg, extension, new File(strFolderStore + "m_" + image.getName()));
				LOG.info("save medium:" + strFolderStore + "m_" + image.getName());
			}

			if (list_size.size() == 2) {
				BufferedImage resizeImageHintJpg = Scalr.resize(originalImage, Scalr.Mode.FIT_EXACT,
						list_size.get(1).getFirst(), list_size.get(1).getSecond(), Scalr.OP_ANTIALIAS);
				ImageIO.write(resizeImageHintJpg, extension, new File(strFolderStore + "s_" + image.getName()));
				LOG.info("save small:" + strFolderStore + "s_" + image.getName());
			}
		}

	}

	private static List<Pair<Integer, Integer>> getHeightSmallAndMedium(BufferedImage originalImage) {
		int heightMedium = 0;
		int heightSmall = 0;
		int width = originalImage.getWidth();
		int height = originalImage.getHeight();

		List<Pair<Integer, Integer>> list = new ArrayList<>();
		Setting setting = new BasicService<>().core().getSettingService().getSetting();

		int widthMediumConf = 550;
		int widthSmallConf = 250;
		if (widthMediumConf != 0) {
			double tile_medium = (double) width / (double) widthMediumConf;
			heightMedium = (int) (height / tile_medium);
			Pair<Integer, Integer> pair_medium = new Pair<>(widthMediumConf, heightMedium);
			list.add(pair_medium);
		}
		if (widthSmallConf != 0) {
			double tile_small = (double) width / (double) widthSmallConf;
			heightSmall = (int) Math.round(height / tile_small);
			Pair<Integer, Integer> pair_small = new Pair<>(widthSmallConf, heightSmall);
			list.add(pair_small);
		}

		return list;
	}

}
