package org.electric.dao;

import org.electric.model.DetectedDevicePicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetectedDevicePictureDao extends JpaRepository<DetectedDevicePicture, String> {
}
