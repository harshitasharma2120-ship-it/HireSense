package hiresenseapp.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.List;

import org.json.JSONObject;

import hiresenseapp.dao.ResumeAnalysisLogDao;
import hiresenseapp.pojo.ResumeAnalysisLogsPojo;
import hiresenseapp.utils.AffindaAPI;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@WebServlet("/UploadResumeServlet")
@MultipartConfig
public class UploadResumeServlet extends HttpServlet {
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int userId = (Integer) session.getAttribute("userId");

        Part filePart = request.getPart("resume");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

        // ✅ Step 1: Define permanent upload path (inside /resumes folder)
        String uploadDir = "C:\\hiresense_uploads";
        File dir = new File(uploadDir);
        if (!dir.exists()) {
			dir.mkdirs();
		}

        String safeName = fileName.replaceAll("[^a-zA-Z0-9.-]", "");
        File resumeFile = new File(dir, userId + "" + System.currentTimeMillis() + "" + safeName);

        // ✅ Step 2: Delete previous resume (if any)
        
        // ✅ Step 3: Save new resume
        try (InputStream input = filePart.getInputStream(); FileOutputStream out = new FileOutputStream(resumeFile)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }

        try {
            // ✅ Step 4: Analyze resume with Affinda
            String resultJson = AffindaAPI.analyzeResume(resumeFile);
            JSONObject result = new JSONObject(resultJson);
            result.getJSONObject("data").put("resumePath", resumeFile.getAbsolutePath());

            ResumeAnalysisLogDao.saveLog(userId, result.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("userDashboard");
    }
}